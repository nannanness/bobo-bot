package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliScanQRCodeLoginResponseDataVO {
    private String url;
    private String refresh_token;
    private String message;
    private Long timestamp;
    private Integer code;
}
