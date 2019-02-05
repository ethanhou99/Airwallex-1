package com.airwallex.operations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * To present an undo data for the calculator stack.<br/>
 */
public class UndoModel {

    /**
     * Stored undo data
     */
    private List<BigDecimal> undoData;

    private UndoModel() {
    	undoData = new ArrayList<>();
    }

    public UndoModel(BigDecimal value) {
        this();
        undoData.add(value);
    }

    public UndoModel(BigDecimal firstValue, BigDecimal secondValue) {
        this(firstValue);
        undoData.add(secondValue);
    }

    public UndoModel(List<BigDecimal> values) {
        this();
        undoData.addAll(values);
    }

    public List<BigDecimal> data() {
        return Collections.unmodifiableList(undoData);
    }
}
