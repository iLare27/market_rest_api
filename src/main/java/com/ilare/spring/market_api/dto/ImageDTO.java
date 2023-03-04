package com.ilare.spring.market_api.dto;

import com.ilare.spring.market_api.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    Image image;
    byte[] bytes;
}
