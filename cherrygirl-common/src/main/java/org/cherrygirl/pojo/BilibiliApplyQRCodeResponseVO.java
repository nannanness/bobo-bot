package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliApplyQRCodeResponseVO {
    private String qrcodeKey;
    private String url;
}
