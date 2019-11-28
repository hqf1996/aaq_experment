package com.MatrixATest;

import org.ojalgo.array.LongToNumberMap;
import org.ojalgo.array.Primitive64Array;
import org.ojalgo.array.SparseArray;
import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.PrimitiveMatrix;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.ojalgo.matrix.store.SparseStore;
import org.ojalgo.matrix.task.iterative.ConjugateGradientSolver;
import org.ojalgo.netio.BasicLogger;
import org.ojalgo.random.Weibull;
import org.ojalgo.series.BasicSeries;
import org.ojalgo.type.CalendarDateUnit;
import org.ojalgo.type.Stopwatch;

import java.util.Random;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 20:11 2019/10/29
 * @Modified By:
 */
public class ojalgo2 {
    private static final String NON_ZEROS = "{} non-zeroes out of {} matrix elements calculated in {}";
    private static final Random RANDOM = new Random();

    public static void main(final String[] args) {
        final int dim = 3;
        final SparseStore<Double> mtrxA = SparseStore.PRIMITIVE.make(1, dim);
        final SparseStore<Double> mtrxB = SparseStore.PRIMITIVE.make(dim, dim);
        final SparseStore<Double> mtrxC = SparseStore.PRIMITIVE.make(dim, dim);
        final MatrixStore<Double> mtrxZ = MatrixStore.PRIMITIVE.makeZero(dim, dim).get();
        final MatrixStore<Double> mtrxI = MatrixStore.PRIMITIVE.makeIdentity(dim).get();

        // 5 matrices * 100k rows * 100k cols * 8 bytes per element => would be more than 372GB if dense
        // This program runs with default settings of any JVM

//        for (int i = 0; i < dim; i++) {
//            final int j = RANDOM.nextInt(dim);
//            final double val = RANDOM.nextDouble();
//            mtrxA.set(i, j, val);
//        } // Each row of A contains 1 non-zero element at random column
        mtrxA.set(0,0,0.1);
        mtrxA.set(0,1,0.2);
        mtrxA.set(0,2,0.7);
        mtrxB.set(0,0,0.9);
        mtrxB.set(0,1,0.075);
        mtrxB.set(0,2,0.025);
        mtrxB.set(1,0,0.15);
        mtrxB.set(1,1,0.8);
        mtrxB.set(1,2,0.05);
        mtrxB.set(2,0,0.25);
        mtrxB.set(2,1,0.25);
        mtrxB.set(2,2,0.5);
        System.out.println(mtrxA);
        System.out.println(mtrxB);
        System.out.println(mtrxA.multiply(mtrxB));


//        for (int i = 0 ; i < 50 ; ++i) {
//            MatrixStore<Double> multiply = mtrxA.multiply(mtrxB);
//        }


//        for (int j = 0; j < dim; j++) {
//            final int i = RANDOM.nextInt(dim);
//            final double val = RANDOM.nextDouble();
//            mtrxB.set(i, j, val);
//        } // Each column of B contains 1 non-zero element at random row

//        final Stopwatch stopwatch = new Stopwatch();
//
//        BasicLogger.debug();
//        BasicLogger.debug("Sparse-Sparse multiplication");
//        stopwatch.reset();
//        mtrxA.multiply(mtrxB, mtrxC);
//        BasicLogger.debug(NON_ZEROS, mtrxC.nonzeros().estimateSize(), mtrxC.count(), stopwatch.stop(CalendarDateUnit.MILLIS));
//
//        // It's the left matrix that decides the multiplication algorithm,
//        // and it knows nothing about the input/right matrix other than that it implements the required interface.
//        // It could be another sparse matrix as in the example above. It could be a full/dense matrix. Or, it could something else...
//
//        // Let's try an identity matrix...
//
//        BasicLogger.debug();
//        BasicLogger.debug("Sparse-Identity multiplication");
//        stopwatch.reset();
//        mtrxA.multiply(mtrxI, mtrxC);
//        BasicLogger.debug(NON_ZEROS, mtrxC.nonzeros().estimateSize(), mtrxC.count(), stopwatch.stop(CalendarDateUnit.MILLIS));
//
//        // ...or an all zeros matrix...
//
//        BasicLogger.debug();
//        BasicLogger.debug("Sparse-Zero multiplication");
//        stopwatch.reset();
//        mtrxA.multiply(mtrxZ, mtrxC);
//        BasicLogger.debug(NON_ZEROS, mtrxC.nonzeros().estimateSize(), mtrxC.count(), stopwatch.stop(CalendarDateUnit.MILLIS));
//
//        // What if we turn things around so that the identity or zero matrices become "this" (the left matrix)?
//
//        BasicLogger.debug();
//        BasicLogger.debug("Identity-Sparse multiplication");
//        stopwatch.reset();
//        mtrxI.multiply(mtrxB, mtrxC);
//        BasicLogger.debug(NON_ZEROS, mtrxC.nonzeros().estimateSize(), mtrxC.count(), stopwatch.stop(CalendarDateUnit.MILLIS));
//
//        BasicLogger.debug();
//        BasicLogger.debug("Zero-Sparse multiplication");
//        stopwatch.reset();
//        mtrxZ.multiply(mtrxB, mtrxC);
//        BasicLogger.debug(NON_ZEROS, mtrxC.nonzeros().estimateSize(), mtrxC.count(), stopwatch.stop(CalendarDateUnit.MILLIS));
//
//        // Q: Why create identity and zero matrices?
//        // A: The can be used as building blocks for larger logical structures.
//
//        final MatrixStore<Double> mtrxL = mtrxI.logical().right(mtrxA).below(mtrxZ, mtrxB).get();
//
//        // There's much more you can do with that logical builder...
//
//        BasicLogger.debug();
//        BasicLogger.debug("Scale logical structure");
//        stopwatch.reset();
//        final MatrixStore<Double> mtrxScaled = mtrxL.multiply(3.14);
//        BasicLogger.debug("{} x {} matrix scaled in {}", mtrxScaled.countRows(), mtrxScaled.countColumns(), stopwatch.stop(CalendarDateUnit.MILLIS));
//
//        // By now we would have passed 1TB, if dense
//
//        SparseArray.factory(Primitive64Array.FACTORY, dim).make();
//
//        LongToNumberMap.factory(Primitive64Array.FACTORY).make();
//        BasicSeries.INSTANT.build(Primitive64Array.FACTORY);
//        new ConjugateGradientSolver();
    }
}
