package com.zakat.darrusalam.Models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class GaleriDto {
    private MultipartFile ImageFile;
}
