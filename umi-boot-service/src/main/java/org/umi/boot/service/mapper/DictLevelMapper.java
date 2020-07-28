package org.umi.boot.service.mapper;

import org.springframework.stereotype.Service;
import org.umi.boot.commons.utils.LevelUtil;
import org.umi.boot.commons.utils.impl.DefaultLevelUtil;
import org.umi.boot.domain.Dict;
import org.umi.boot.web.rest.vm.DictLevelVM;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DictLevelMapper {

    private LevelUtil<DictLevelVM> levelUtil = new DefaultLevelUtil<>();

    public List<DictLevelVM> adaptToTree(List<Dict> dicts) {
        return levelUtil.listToTree(this.adapt(dicts));
    }

    public List<DictLevelVM> adapt(List<Dict> dicts) {
        return dicts.stream().filter(Objects::nonNull).map(this::adapt).collect(Collectors.toList());
    }

    public DictLevelVM adapt(Dict dict) {
        return DictLevelVM.adapt(dict);
    }
}
