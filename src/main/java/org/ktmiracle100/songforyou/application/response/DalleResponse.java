package org.ktmiracle100.songforyou.application.response;

import java.util.List;
import java.util.Map;

public record DalleResponse(
        Long created,
        List<Map<String, Object>> data
) {

}
