package org.umi.boot.commons.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.umi.boot.commons.info.enums.ShowType;

import java.io.Serializable;

public interface Info {

    static InfoStructure success(Object data) {
        return new InfoStructure(true, data);
    }

    static InfoStructure success(Object data, String message) {
        return new InfoStructure(true, data, message, ShowType.WARN_MESSAGE.getShowType());
    }

    static InfoStructure success(Object data, String message, ShowType showType) {
        return new InfoStructure(true, data, message, showType.getShowType());
    }

    static InfoStructure success(Page<?> page) {
        return new InfoStructure(true, new PaginationData(page.getContent(), page.getTotalElements()));
    }

    static InfoStructure error(String errorCode, String message, String e) {
        return new InfoStructure(false, errorCode, message, ShowType.ERROR_MESSAGE.getShowType(), e);
    }

    static InfoStructure error(String errorCode, String message, String e, ShowType showType) {
        return new InfoStructure(false, errorCode, message, showType.getShowType(), e);
    }

    static InfoStructure error(String errorCode, String message, String e, String traceId, String host) {
        return new InfoStructure(false, errorCode, message, ShowType.ERROR_MESSAGE.getShowType(), e, traceId, host);
    }

    static InfoStructure error(String errorCode, String message, String e, ShowType showType, String traceId, String host) {
        return new InfoStructure(false, errorCode, message, showType.getShowType(), e, traceId, host);
    }

    @ApiModel(description = "分页数据模型", value = "PaginationData")
    @Data
    class PaginationData implements Serializable {

        private static final long serialVersionUID = 2205170132541309365L;

        @ApiModelProperty(value = "当前页数据", required = true)
        private Object list;

        @ApiModelProperty(value = "数据总行数", required = true)
        private Long total;

        public PaginationData(Object list, Long total) {
            this.list = list;
            this.total = total;
        }
    }
}
