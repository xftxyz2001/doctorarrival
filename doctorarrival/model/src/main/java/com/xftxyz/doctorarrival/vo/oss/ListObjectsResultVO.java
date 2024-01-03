package com.xftxyz.doctorarrival.vo.oss;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListObjectsResultVO {

    private List<OSSObjectSummaryVO> objectSummaries = new ArrayList<>();

    private int keyCount;

    private String nextContinuationToken;
}
