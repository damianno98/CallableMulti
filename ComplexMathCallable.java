package com.tt.concurrent.callable;

import java.util.concurrent.Callable;

public class ComplexMathCallable  implements Callable<Double> {
    private ComplexMath complexMath;
    private int numberOfThreads;
    private int threadNumber;

    public ComplexMathCallable (ComplexMath complexMath, int threadNumber, int numberOfThreads){
        this.complexMath = complexMath;
        this.threadNumber = threadNumber;
        this.numberOfThreads = numberOfThreads;
    }


    @Override
    public Double call() throws Exception {
        int columnNumber = complexMath.getNoColumns();
        int partToDo = columnNumber / numberOfThreads;
        double result = 0.;
        if(threadNumber == numberOfThreads - 1) {
            for(int i = threadNumber * partToDo; i < columnNumber; i++) {
                result += (complexMath.doComplexMathForColumn(i));
            }
        }
        else {
            for (int i = threadNumber * partToDo; i < partToDo + threadNumber * partToDo; i++) {
                result += (complexMath.doComplexMathForColumn(i));
            }
        }
        return result;
    }
}
