package org.umi.boot.commons.info.enums;

import lombok.Getter;

@Getter
public enum ShowType {
    SILENT(0), WARN_MESSAGE(1), ERROR_MESSAGE(2), NOTIFICATION(4), REDIRECT(9);

    private final Integer showType;

    ShowType(Integer showType) {
        this.showType = showType;
    }
}
