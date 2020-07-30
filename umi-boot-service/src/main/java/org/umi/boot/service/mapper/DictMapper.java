package org.umi.boot.service.mapper;

import org.springframework.stereotype.Service;
import org.umi.boot.domain.Dict;
import org.umi.boot.web.rest.vm.DictVM;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DictMapper {

    public List<DictVM> adapt(List<Dict> dicts) {
        return dicts.stream().filter(Objects::nonNull).distinct().map(this::adapt).collect(Collectors.toList());
    }

    public DictVM adapt(Dict dict) {
        return DictVM.adapt(dict);
    }
}
