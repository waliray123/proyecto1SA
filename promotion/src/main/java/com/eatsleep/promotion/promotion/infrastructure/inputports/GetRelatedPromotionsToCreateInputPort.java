package com.eatsleep.promotion.promotion.infrastructure.inputports;

import com.eatsleep.promotion.promotion.infrastructure.inputadapters.restapi.response.RelatedPromotionToCreateResponse;
import java.util.List;

public interface GetRelatedPromotionsToCreateInputPort {
    List<RelatedPromotionToCreateResponse> getRelatedPromotionsToCreateInputPort();
}
