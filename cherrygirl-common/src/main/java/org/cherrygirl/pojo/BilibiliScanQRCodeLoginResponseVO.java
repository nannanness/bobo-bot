package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliScanQRCodeLoginResponseVO {

    private Integer code;
    private String message;
    private BilibiliScanQRCodeLoginResponseDataVO data;
}
