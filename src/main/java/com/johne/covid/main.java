package com.johne.covid;

import com.johne.covid.util.RiskAreaUtil;

/**
 * @Author: johne
 * @Date: 2022/11/4 15:38
 * Copyright (c) 2022 tencent
 */
public class main {
    public static void main(String[] args) {
        System.out.print("hello");
        String risk = RiskAreaUtil.get_risk();
        System.out.printf(risk);
    }
}
