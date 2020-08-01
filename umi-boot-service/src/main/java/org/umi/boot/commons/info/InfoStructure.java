package org.umi.boot.commons.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "接口消息模型", value = "InfoStructure")
@Data
public class InfoStructure implements Serializable {

    private static final long serialVersionUID = -6005192455561666565L;

    @ApiModelProperty(value = "请求状态", required = true)
    private Boolean success;

    @ApiModelProperty(value = "响应数据")
    private Object data;

    @ApiModelProperty(value = "异常代码")
    private String errorCode;

    @ApiModelProperty(value = "异常消息")
    private String errorMessage;

    @ApiModelProperty(value = "提示类型")
    private Integer showType;

    @ApiModelProperty(value = "异常信息")
    private String e;

    @ApiModelProperty(value = "请求编号")
    private String traceId;

    @ApiModelProperty(value = "接口地址")
    private String host;

    public InfoStructure(Boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public InfoStructure(Boolean success, Object data, String errorMessage, Integer showType) {
        this.success = success;
        this.data = data;
        this.errorMessage = errorMessage;
        this.showType = showType;
    }

    public InfoStructure(Boolean success, String errorCode, String errorMessage, Integer showType, String e) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.showType = showType;
        this.e = e;
    }

    public InfoStructure(Boolean success, String errorCode, String errorMessage, Integer showType, String e, String traceId, String host) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.showType = showType;
        this.e = e;
        this.traceId = traceId;
        this.host = host;
    }
}
