package com.z.model;

public class Payroll implements IPayroll {
    @Override
    public void generatePayStatement() {
        System.out.println("Generating pay statement...");
    }
}