package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliVideoStreamResponseDataUrlVO {
    private String url;
    private List<String> backup_url;
}
