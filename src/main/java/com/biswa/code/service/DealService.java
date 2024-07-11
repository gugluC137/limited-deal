package com.biswa.code.service;

import com.biswa.code.dto.CreateDealRequestDto;
import com.biswa.code.dto.DealUpdateRequestDto;
import com.biswa.code.dto.ProductDealRequestDto;
import com.biswa.code.dto.UpdateRequest;
import com.biswa.code.exception.*;
import com.biswa.code.model.Deal;
import com.biswa.code.model.ProductWithDeal;
import com.biswa.code.repo.DealRepo;
import com.biswa.code.repo.ProductWithDealRepo;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static com.biswa.code.exception.ErrorMessageConstants.*;

@AllArgsConstructor
public class DealService {
    private final DealRepo dealRepo;
    private final ProductWithDealRepo productWithDealRepo;

    private final ProductService productService;

    public String createNewDeal(@NonNull final CreateDealRequestDto requestDto) {
        Date startDate = Objects.isNull(requestDto.getStartDate())
            ? new Date(System.currentTimeMillis())
            : requestDto.getStartDate();

        Integer periodInMin = Objects.isNull(requestDto.getPeriodInMin())
            ? dealRepo.getDefaultDealPeriodInMin()
            : requestDto.getPeriodInMin();

        Date endDate = getEndDate(startDate, periodInMin);
        String dealId = UUID.randomUUID().toString();
        //String id = String.valueOf(System.currentTimeMillis());

        Deal newDeal = new Deal(dealId, requestDto.getName(), startDate, endDate);
        dealRepo.addNewDeal(newDeal);

        for (ProductDealRequestDto pdrRequest : requestDto.getProductDealList()) {
            addProductWIthDeal(dealId, pdrRequest);
        }

        return dealId;
    }

    private Date getEndDate(Date startDate, int periodInMin) {
        return new DateTime(startDate).plusMinutes(periodInMin).toDate();
    }

    private void addProductWIthDeal(String dealId, ProductDealRequestDto pdrRequest) {
        if (productService.isProductPresent(pdrRequest.getProductId())) {
            throw new LimitedDealException(INVALID_PRODUCT_ID);
        }

        ProductWithDeal pwd = new ProductWithDeal(pdrRequest.getProductId(), dealId, pdrRequest.getQuantity(), pdrRequest.getDealPrice());
        productWithDealRepo.storeNewProductWIthDeal(pwd);
    }

    public void endDeal(@NonNull String dealId) {
        getDealById(dealId);
        if (isDealExpired(dealId)) {
            throw new LimitedDealException(INACTIVE_DEAL);
        }

        dealRepo.updateEndDate(dealId, DateTime.now().toDate());
    }

    public Deal getDealById(@NonNull String dealId) {
        try {
            return dealRepo.getDealById(dealId);
        } catch (InvalidDealIdException ex) {
            throw new LimitedDealException(INVALID_DEAL_ID);
        }
    }

    public boolean isDealExpired(@NonNull String dealId) {
        Deal deal = getDealById(dealId);
        DateTime startDateTime = new DateTime(deal.getStartDate());
        DateTime endDateTime = new DateTime(deal.getEndDate());

        return DateTime.now().isBefore(startDateTime) || DateTime.now().isAfter(endDateTime);
    }

    public void claimDeal(@NonNull String dealId, @NonNull String productId) {
        //check if deal is active
        if (isDealExpired(dealId)) {
            throw new InactiveDealException();
        }

        //check if product is available
        ProductWithDeal pwd = productWithDealRepo.getProductInDeal(dealId, productId);
        if (pwd.getQuantity() == 0) {
            throw new LimitedDealException(PRODUCT_SOLD_OUT);
        }

        //decrease product quantity by 1
        productWithDealRepo.updateQuantity(dealId, productId, pwd.getQuantity()-1);
    }

    public void updateDeal(DealUpdateRequestDto requestDto) {
        getDealById(requestDto.getDealId());
        if (isDealExpired(requestDto.getDealId())) {
            throw new LimitedDealException(INACTIVE_DEAL);
        }

        for (UpdateRequest req : requestDto.getUpdateRequestList()) {
            switch (req.getUpdateType()) {
                case END_DATE :
                    updateEndDateForDeal(requestDto.getDealId(), req.getUpdateValue());
                    break;
                case PRODUCT_QUANTITY:
                    updateProductQuantity(requestDto.getDealId(), req.getId(), req.getUpdateValue());
                    break;
                case PRODUCT_PRICE:
                    updateProductPrice(requestDto.getDealId(), req.getId(), req.getUpdateValue());
                    break;
                default: throw new LimitedDealException(INVALID_UPDATE_TYPE);
            }
        }
    }

    public void updateEndDateForDeal(String dealId, String newDate) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newEndDate = df.parse(newDate);

            dealRepo.updateEndDate(dealId, newEndDate);
        } catch (ParseException ex) {
            throw new LimitedDealException(INVALID_END_DATE);
        }
    }

    public void updateProductQuantity(String dealId, String productId, String updatedValue) {
        try {
            ProductWithDeal pwd = productWithDealRepo.getProductInDeal(dealId, productId);
            int q = Integer.parseInt(updatedValue);
            productWithDealRepo.updateQuantity(dealId, productId, pwd.getQuantity() + q);
        } catch (ProductNotInDealException ex) {
            throw new LimitedDealException(PRODUCT_NOT_IN_DEAL);
        }
    }

    public void updateProductPrice(String dealId, String productId, String updateValue) {
        try {
            ProductWithDeal pwd = productWithDealRepo.getProductInDeal(dealId, productId);
            double newPrice = Double.parseDouble(updateValue);
            productWithDealRepo.updatePrice(dealId, productId, newPrice);
        } catch (ProductNotInDealException ex) {
            throw new LimitedDealException(PRODUCT_NOT_IN_DEAL);
        }
    }
}
