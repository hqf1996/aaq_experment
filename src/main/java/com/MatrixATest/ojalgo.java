package com.MatrixATest;

import org.ojalgo.OjAlgoUtils;
import org.ojalgo.RecoverableCondition;
import org.ojalgo.array.LongToNumberMap;
import org.ojalgo.array.Primitive64Array;
import org.ojalgo.array.SparseArray;
import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.PrimitiveMatrix;
import org.ojalgo.matrix.decomposition.QR;
import org.ojalgo.matrix.store.*;
import org.ojalgo.matrix.task.InverterTask;
import org.ojalgo.matrix.task.SolverTask;
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
public class ojalgo {
    private static final String NON_ZEROS = "{} non-zeroes out of {} matrix elements calculated in {}";
    private static final Random RANDOM = new Random();

    public static void main(final String[] args) {

        BasicMatrix.Factory<PrimitiveMatrix> matrixFactory =
                PrimitiveMatrix.FACTORY;
        PhysicalStore.Factory<Double, PrimitiveDenseStore> storeFactory =
                PrimitiveDenseStore.FACTORY;
        // BasicMatrix.Factory and PhysicalStore.Factory are very similar.
        // Every factory in ojAlgo that makes 2D-structures
        // extends/implements the same interface.

        PrimitiveMatrix matrixA = matrixFactory.makeEye(5, 5);
        // Internally this creates an "eye-structure" - not a large array.
        PrimitiveDenseStore storeA = storeFactory.makeEye(5, 3);
        // A PrimitiveDenseStore is always a "full array". No smart data
        // structures here.

        PrimitiveMatrix matrixB =
                matrixFactory.makeFilled(5, 3, new Weibull(5.0, 2.0));
        PrimitiveDenseStore storeB =
                storeFactory.makeFilled(3, 3, new Weibull(5.0, 2.0));
        // When you create a matrix with random elements you can specify
        // their distribution.

        /* Matrix multiplication */

        PrimitiveMatrix matrixC = matrixA.multiply(matrixB);
        // Multiplying two PrimitiveMatrix:s is trivial. There are no
        // alternatives, and the returned product is a PrimitiveMatrix
        // (same as the inputs).

        // Doing the same thing using PrimitiveDenseStore (MatrixStore) you
        // have options...

        BasicLogger.debug("Different ways to do matrix multiplication with " +
                "MatrixStore:s");
        BasicLogger.debug();
        MatrixStore<Double> storeC = storeA.multiply(storeB);
        MatrixStore<Double> storeCC = storeA.multiply(storeB).multiply(storeB);
        // One option is to do exactly what you did with PrimitiveMatrix.
        // The only difference is that the return type is MatrixStore rather
        // than PhysicalStore, PrimitiveDenseStore or whatever else you input.
//        BasicLogger.debug("MatrixStore MatrixStore#multiply(MatrixStore)",
//                storeC);

        System.out.println(storeA);
        storeA.set(0,0,0.99);
        System.out.println(storeA);
//        System.out.println(storeB);
//        System.out.println(storeC);
//        System.out.println(storeCC);
//        PrimitiveDenseStore storeCPreallocated = storeFactory.makeZero(5, 3);
//        // Another option is to first create the matrix that should hold the
//        // resulting product,
//        storeA.multiply(storeB, storeCPreallocated);
//        // and then perform the multiplication. This enables reusing memory
//        // (the product matrix).
//        BasicLogger.debug(
//                "void MatrixStore#multiply(Access1D, ElementsConsumer)",
//                storeCPreallocated);
//
//        ElementsSupplier<Double> storeCSupplier = storeB.premultiply(storeA);
//        // A third option is the premultiply method:
//        // 1) The left and right argument matrices are interchanged.
//        // 2) The return type is an ElementsSupplier rather than
//        //    a MatrixStore.
//        // This is because the multiplication is not yet performed.
//        // It is possible to define additional operation on
//        // an ElementsSupplier.
//        MatrixStore<Double> storeCLater = storeCSupplier.get();
//        // The multiplication, and whatever additional operations you defined,
//        // is performed when you call #get().
//        BasicLogger.debug(
//                "ElementsSupplier MatrixStore#premultiply(Access1D)",
//                storeCLater);
//
//        // A couple of more alternatives that will do the same thing.
//        storeCPreallocated.fillByMultiplying(storeA, storeB);
//        BasicLogger.debug(
//                "void ElementsConsumer#fillByMultiplying(Access1D, Access1D)",
//                storeCPreallocated);
//        storeCSupplier.supplyTo(storeCPreallocated);
//        BasicLogger.debug("void ElementsSupplier#supplyTo(ElementsConsumer)",
//                storeCPreallocated);
//        System.out.println(matrixA);
//        matrixA.invert();
//        System.out.println(matrixA);

//        // With MatrixStore:s you need to use an InverterTask
//        InverterTask<Double> inverter = InverterTask.PRIMITIVE.make(storeA);
//        // There are many implementations of that interface. This factory
//        // method will return one that may be suitable, but most likely you
//        // will want to choose implementation based on what you know about
//        // the matrix.
//        try {
//            inverter.invert(storeA);
//        } catch (RecoverableCondition e) {
//            // Will throw and exception if inversion fails, rethrowing it.
//            throw new RuntimeException(e);
//        }
//
//        matrixA.solve(matrixC);
//
//        SolverTask<Double> solver = SolverTask.PRIMITIVE.make(storeA, storeC);
//        try {
//            solver.solve(storeA, storeC);
//        } catch (RecoverableCondition e) {
//            // Will throw and exception if solving fails, rethrowing it.
//            throw new RuntimeException(e);
//        }
//
//        // Most likely you want to do is to instantiate some matrix
//        // decomposition (there are several).
//
//        QR<Double> qr = QR.PRIMITIVE.make(storeA);
//        qr.decompose(storeA);
//        if (qr.isSolvable()) {
//            qr.getSolution(storeC);
//        } else {
//            // You should verify that the equation system is solvable,
//            // and do something else if it is not.
//            throw new RuntimeException("Cannot solve the equation system");
//        }
//
//        /* Setting individual elements */
//
//        storeA.set(3, 1, 3.14);
//        storeA.set(3, 0, 2.18);
//        // PhysicalStore instances are naturally mutable. If you want to set
//        // or modify something - just do it
//
//        BasicMatrix.Builder<PrimitiveMatrix> matrixBuilder = matrixA.copy();
//        // PrimitiveMatrix is immutable. To modify anything, you need to copy
//        // it to Builder instance.
//
//        matrixBuilder.add(3, 1, 3.14);
//        matrixBuilder.add(3, 0, 2.18);
//
//        matrixBuilder.build();
//
//        /* Creating matrices by explicitly setting all elements */
//
        double[][] tmpData = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0},
                {7.0, 8.0, 9.0}
        };
//
//        matrixFactory.rows(tmpData);
//        storeFactory.rows(tmpData);
//
//        // If you don't want/need to first create some (intermediate) array
//        // for the elements, you can of course set them on the matrix
//        // directly.
//        PrimitiveDenseStore storeZ = storeFactory.makeEye(3, 3);
//
//        // Since PrimitiveMatrix is immutable this has to be done via
//        // a builder.
//        BasicMatrix.Builder<PrimitiveMatrix> matrixZBuilder =
//                matrixFactory.getBuilder(3, 3);
//
//        for (int j = 0; j < 3; j++) {
//            for (int i = 0; i < 3; i++) {
//                matrixZBuilder.set(i, j, i * j);
//                storeZ.set(i, j, i * j);
//            }
//        }
//
//        matrixZBuilder.get();

    }
}
