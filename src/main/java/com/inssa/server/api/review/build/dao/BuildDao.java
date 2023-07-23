package com.inssa.server.api.review.build.dao;


import com.inssa.server.api.review.build.dto.BuildDto;
import com.inssa.server.api.review.build.mapper.BuildMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BuildDao {

    private final BuildMapper buildMapper;

    public List<BuildDto> selectList() {
        return buildMapper.selectList();
    }
}
