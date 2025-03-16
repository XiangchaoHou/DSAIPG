package com.phasmidsoftware.dsaipg.mainForAssignment;

import com.phasmidsoftware.dsaipg.adt.pq.FibonacciHeap;
import com.phasmidsoftware.dsaipg.adt.pq.PQException;
import com.phasmidsoftware.dsaipg.adt.pq.PriorityQueue4ary;
import com.phasmidsoftware.dsaipg.adt.pq.PriorityQueue;
import com.phasmidsoftware.dsaipg.util.Benchmark_Timer;

import java.util.*;

public class mainForAssignment4 {
    public static void main(String[] args) {
        final int M = 4095;
        final int I = 16000;
        final int R = 4000;
        boolean max = true;
        boolean floyd = true;

        Random random = new Random();
        Integer[] r = new Integer[I*16];
        for (int i = 0; i < I*16; i++) {
            r[i] = random.nextInt(10000);
        }
        Integer[] e1 = new Integer[M+1];
        Integer[] e2 = new Integer[(M+1)*2];
        Integer[] e3 = new Integer[(M+1)*4];
        Integer[] e4 = new Integer[(M+1)*8];
        Integer[] e5 = new Integer[(M+1)*16];

        Arrays.fill(e1, null);
        Arrays.fill(e2, null);
        Arrays.fill(e3, null);
        Arrays.fill(e4, null);
        Arrays.fill(e5, null);

        List<Integer> spilledBin = new ArrayList<>();
        List<Integer> spilledFloydBin = new ArrayList<>();
        List<Integer> spilledFary = new ArrayList<>();
        List<Integer> spilledFloydFary = new ArrayList<>();
        int[] t1 = new int[5];
        int[] t2 = new int[5];
        int[] t3 = new int[5];
        int[] t4 = new int[5];

        Benchmark_Timer b1 = new Benchmark_Timer("BinHeap1", b-> {
            PriorityQueue<Integer> binHeap = new PriorityQueue<>(max, e1, 1, 0, Comparator.comparing(Integer::intValue), false);
            for (int i = 0; i < I; i++) {
                if( i >= M ) {
                    spilledBin.add(e1[M]);
                }
                binHeap.give(r[i]);
            }
            for (int i = 0; i < R; i++) {
                try {
                    binHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e1, null);
        });
        double bt1 = b1.run(false, 200);
        Collections.sort(spilledBin);
        t1[0] = spilledBin.get(spilledBin.size() - 1);

        Benchmark_Timer b2 = new Benchmark_Timer("BinHeap2", b-> {
            PriorityQueue<Integer> binHeap = new PriorityQueue<>(max, e2, 1, 0, Comparator.comparing(Integer::intValue), false);
            for (int i = 0; i < I*2; i++) {
                if( i >= M*2 ) {
                    spilledBin.add(e2[M]);
                }
                binHeap.give(r[i]);
            }
            for (int i = 0; i < R*2; i++) {
                try {
                    binHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e2, null);
        });
        double bt2 = b2.run(false, 200);
        Collections.sort(spilledBin);
        t1[1] = spilledBin.get(spilledBin.size() - 1);

        Benchmark_Timer b3 = new Benchmark_Timer("BinHeap4", b-> {
            PriorityQueue<Integer> binHeap = new PriorityQueue<>(max, e3, 1, 0, Comparator.comparing(Integer::intValue), false);
            for (int i = 0; i < I*4; i++) {
                if( i >= M*4 ) {
                    spilledBin.add(e3[M]);
                }
                binHeap.give(r[i]);
            }
            for (int i = 0; i < R*4; i++) {
                try {
                    binHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e3, null);
        });
        double bt3 = b3.run(false, 200);
        Collections.sort(spilledBin);
        t1[2] = spilledBin.get(spilledBin.size() - 1);

        Benchmark_Timer b4 = new Benchmark_Timer("BinHeap8", b-> {
            PriorityQueue<Integer> binHeap = new PriorityQueue<>(max, e4, 1, 0, Comparator.comparing(Integer::intValue), false);
            for (int i = 0; i < I*8; i++) {
                if( i >= M*8 ) {
                    spilledBin.add(e4[M]);
                }
                binHeap.give(r[i]);
            }
            for (int i = 0; i < R*8; i++) {
                try {
                    binHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e4, null);
        });
        double bt4 = b4.run(false, 200);
        Collections.sort(spilledBin);
        t1[3] = spilledBin.get(spilledBin.size() - 1);

        Benchmark_Timer b5 = new Benchmark_Timer("BinHeap16", b-> {
            PriorityQueue<Integer> binHeap = new PriorityQueue<>(max, e5, 1, 0, Comparator.comparing(Integer::intValue), false);
            for (int i = 0; i < I*16; i++) {
                if( i >= M*16 ) {
                    spilledBin.add(e5[M]);
                }
                binHeap.give(r[i]);
            }
            for (int i = 0; i < R*16; i++) {
                try {
                    binHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e5, null);
        });
        double bt5 = b5.run(false, 200);
        Collections.sort(spilledBin);
        t1[4] = spilledBin.get(spilledBin.size() - 1);
        System.out.println("MaxSpilled: " + t1[0] + ", " + t1[1] + ", " + t1[2] + ", " + t1[3] + ", " + t1[4]);
        System.out.printf("BinHeap: %.3fms, %.3fms, %.3fms, %.3fms, %.3fms\n\n", bt1, bt2, bt3, bt4, bt5);

        Benchmark_Timer fb1 = new Benchmark_Timer("FloydBinHeap1", b-> {
            PriorityQueue<Integer> floydBinHeap = new PriorityQueue<>(max, e1, 1, 0, Comparator.comparing(Integer::intValue), floyd);
            for (int i = 0; i < I; i++) {
                if( i >= M ) {
                    spilledFloydBin.add(e1[M]);
                }
                floydBinHeap.give(r[i]);
            }
            for (int i = 0; i < R; i++) {
                try {
                    floydBinHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e1, null);
        });
        double fbt1 = fb1.run(false, 200);
        Collections.sort(spilledFloydBin);
        t2[0] = spilledFloydBin.get(spilledFloydBin.size() - 1);

        Benchmark_Timer fb2 = new Benchmark_Timer("FloydBinHeap2", b-> {
            PriorityQueue<Integer> floydBinHeap = new PriorityQueue<>(max, e2, 1, 0, Comparator.comparing(Integer::intValue), floyd);
            for (int i = 0; i < I*2; i++) {
                if( i >= M*2 ) {
                    spilledFloydBin.add(e2[M]);
                }
                floydBinHeap.give(r[i]);
            }
            for (int i = 0; i < R*2; i++) {
                try {
                    floydBinHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e2, null);
        });
        double fbt2 = fb2.run(false, 200);
        Collections.sort(spilledFloydBin);
        t2[1] = spilledFloydBin.get(spilledFloydBin.size() - 1);

        Benchmark_Timer fb3 = new Benchmark_Timer("FloydBinHeap4", b-> {
            PriorityQueue<Integer> floydBinHeap = new PriorityQueue<>(max, e3, 1, 0, Comparator.comparing(Integer::intValue), floyd);
            for (int i = 0; i < I*4; i++) {
                if( i >= M*4 ) {
                    spilledFloydBin.add(e3[M]);
                }
                floydBinHeap.give(r[i]);
            }
            for (int i = 0; i < R*4; i++) {
                try {
                    floydBinHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e3, null);
        });
        double fbt3 = fb3.run(false, 200);
        Collections.sort(spilledFloydBin);
        t2[2] = spilledFloydBin.get(spilledFloydBin.size() - 1);

        Benchmark_Timer fb4 = new Benchmark_Timer("FloydBinHeap8", b-> {
            PriorityQueue<Integer> floydBinHeap = new PriorityQueue<>(max, e4, 1, 0, Comparator.comparing(Integer::intValue), floyd);
            for (int i = 0; i < I*8; i++) {
                if( i >= M*8 ) {
                    spilledFloydBin.add(e4[M]);
                }
                floydBinHeap.give(r[i]);
            }
            for (int i = 0; i < R*8; i++) {
                try {
                    floydBinHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e4, null);
        });
        double fbt4 = fb4.run(false, 200);
        Collections.sort(spilledFloydBin);
        t2[3] = spilledFloydBin.get(spilledFloydBin.size() - 1);

        Benchmark_Timer fb5 = new Benchmark_Timer("FloydBinHeap16", b-> {
            PriorityQueue<Integer> floydBinHeap = new PriorityQueue<>(max, e5, 1, 0, Comparator.comparing(Integer::intValue), floyd);
            for (int i = 0; i < I*16; i++) {
                if( i >= M*16 ) {
                    spilledFloydBin.add(e5[M]);
                }
                floydBinHeap.give(r[i]);
            }
            for (int i = 0; i < R*16; i++) {
                try {
                    floydBinHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e5, null);
        });
        double fbt5 = fb5.run(false, 200);
        Collections.sort(spilledFloydBin);
        t2[4] = spilledFloydBin.get(spilledFloydBin.size() - 1);
        System.out.println("MaxSpilled: " + t2[0] + ", " + t2[1] + ", " + t2[2] + ", " + t2[3] + ", " + t2[4]);
        System.out.printf("FloydBinHeap: %.3fms, %.3fms, %.3fms, %.3fms, %.3fms\n\n", fbt1, fbt2, fbt3, fbt4, fbt5);

        Benchmark_Timer f1 = new Benchmark_Timer("FaryHeap1", b-> {
            PriorityQueue4ary<Integer> faryHeap = new PriorityQueue4ary<>(max, e1, 1, 0, Comparator.comparing(Integer::intValue), false);
            for (int i = 0; i < I; i++) {
                if( i >= M ) {
                    spilledFary.add(e1[M]);
                }
                faryHeap.give(r[i]);
            }
            for (int i = 0; i < R; i++) {
                try {
                    faryHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e1, null);
        });
        double ft1 = f1.run(false, 200);
        Collections.sort(spilledFary);
        t3[0] = spilledFary.get(spilledFary.size() - 1);

        Benchmark_Timer f2 = new Benchmark_Timer("FaryHeap2", b-> {
            PriorityQueue4ary<Integer> faryHeap = new PriorityQueue4ary<>(max, e2, 1, 0, Comparator.comparing(Integer::intValue), false);
            for (int i = 0; i < I*2; i++) {
                if( i >= M*2 ) {
                    spilledFary.add(e2[M]);
                }
                faryHeap.give(r[i]);
            }
            for (int i = 0; i < R*2; i++) {
                try {
                    faryHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e2, null);
        });
        double ft2 = f2.run(false, 200);
        Collections.sort(spilledFary);
        t3[1] = spilledFary.get(spilledFary.size() - 1);

        Benchmark_Timer f3 = new Benchmark_Timer("FaryHeap4", b-> {
            PriorityQueue4ary<Integer> faryHeap = new PriorityQueue4ary<>(max, e3, 1, 0, Comparator.comparing(Integer::intValue), false);
            for (int i = 0; i < I*4; i++) {
                if( i >= M*4 ) {
                    spilledFary.add(e3[M]);
                }
                faryHeap.give(r[i]);
            }
            for (int i = 0; i < R*4; i++) {
                try {
                    faryHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e3, null);
        });
        double ft3 = f3.run(false, 200);
        Collections.sort(spilledFary);
        t3[2] = spilledFary.get(spilledFary.size() - 1);

        Benchmark_Timer f4 = new Benchmark_Timer("FaryHeap8", b-> {
            PriorityQueue4ary<Integer> faryHeap = new PriorityQueue4ary<>(max, e4, 1, 0, Comparator.comparing(Integer::intValue), false);
            for (int i = 0; i < I*8; i++) {
                if( i >= M*8 ) {
                    spilledFary.add(e4[M]);
                }
                faryHeap.give(r[i]);
            }
            for (int i = 0; i < R*8; i++) {
                try {
                    faryHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e4, null);
        });
        double ft4 = f4.run(false, 200);
        Collections.sort(spilledFary);
        t3[3] = spilledFary.get(spilledFary.size() - 1);

        Benchmark_Timer f5 = new Benchmark_Timer("FaryHeap16", b-> {
            PriorityQueue4ary<Integer> faryHeap = new PriorityQueue4ary<>(max, e5, 1, 0, Comparator.comparing(Integer::intValue), false);
            for (int i = 0; i < I*16; i++) {
                if( i >= M*16 ) {
                    spilledFary.add(e5[M]);
                }
                faryHeap.give(r[i]);
            }
            for (int i = 0; i < R*16; i++) {
                try {
                    faryHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e5, null);
        });
        double ft5 = f5.run(false, 200);
        Collections.sort(spilledFary);
        t3[4] = spilledFary.get(spilledFary.size() - 1);
        System.out.println("MaxSpilled: " + t3[0] + ", " + t3[1] + ", " + t3[2] + ", " + t3[3] + ", " + t3[4]);
        System.out.printf("FaryHeap: %.3fms, %.3fms, %.3fms, %.3fms, %.3fms\n\n", ft1, ft2, ft3, ft4, ft5);

        Benchmark_Timer ff1 = new Benchmark_Timer("FloydFaryHeap1", b-> {
            PriorityQueue4ary<Integer> floydfaryHeap = new PriorityQueue4ary<>(max, e1, 1, 0, Comparator.comparing(Integer::intValue), floyd);
            for (int i = 0; i < I; i++) {
                if( i >= M ) {
                    spilledFloydFary.add(e1[M]);
                }
                floydfaryHeap.give(r[i]);
            }
            for (int i = 0; i < R; i++) {
                try {
                    floydfaryHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e1, null);
        });
        double fft1 = ff1.run(false, 200);
        Collections.sort(spilledFloydFary);
        t4[0] = spilledFloydFary.get(spilledFloydFary.size() - 1);

        Benchmark_Timer ff2 = new Benchmark_Timer("FloydFaryHeap2", b-> {
            PriorityQueue4ary<Integer> floydfaryHeap = new PriorityQueue4ary<>(max, e2, 1, 0, Comparator.comparing(Integer::intValue), floyd);
            for (int i = 0; i < I*2; i++) {
                if( i >= M*2 ) {
                    spilledFloydFary.add(e2[M]);
                }
                floydfaryHeap.give(r[i]);
            }
            for (int i = 0; i < R*2; i++) {
                try {
                    floydfaryHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e2, null);
        });
        double fft2 = ff2.run(false, 200);
        Collections.sort(spilledFloydFary);
        t4[1] = spilledFloydFary.get(spilledFloydFary.size() - 1);

        Benchmark_Timer ff3 = new Benchmark_Timer("FloydFaryHeap4", b-> {
            PriorityQueue4ary<Integer> floydfaryHeap = new PriorityQueue4ary<>(max, e3, 1, 0, Comparator.comparing(Integer::intValue), floyd);
            for (int i = 0; i < I*4; i++) {
                if( i >= M*4 ) {
                    spilledFloydFary.add(e3[M]);
                }
                floydfaryHeap.give(r[i]);
            }
            for (int i = 0; i < R*4; i++) {
                try {
                    floydfaryHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e3, null);
        });
        double fft3 = ff3.run(false, 200);
        Collections.sort(spilledFloydFary);
        t4[2] = spilledFloydFary.get(spilledFloydFary.size() - 1);

        Benchmark_Timer ff4 = new Benchmark_Timer("FloydFaryHeap8", b-> {
            PriorityQueue4ary<Integer> floydfaryHeap = new PriorityQueue4ary<>(max, e4, 1, 0, Comparator.comparing(Integer::intValue), floyd);
            for (int i = 0; i < I*8; i++) {
                if( i >= M*8 ) {
                    spilledFloydFary.add(e4[M]);
                }
                floydfaryHeap.give(r[i]);
            }
            for (int i = 0; i < R*8; i++) {
                try {
                    floydfaryHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e4, null);
        });
        double fft4 = ff4.run(false, 200);
        Collections.sort(spilledFloydFary);
        t4[3] = spilledFloydFary.get(spilledFloydFary.size() - 1);

        Benchmark_Timer ff5 = new Benchmark_Timer("FloydFaryHeap16", b-> {
            PriorityQueue4ary<Integer> floydfaryHeap = new PriorityQueue4ary<>(max, e5, 1, 0, Comparator.comparing(Integer::intValue), floyd);
            for (int i = 0; i < I*16; i++) {
                if( i >= M*16 ) {
                    spilledFloydFary.add(e5[M]);
                }
                floydfaryHeap.give(r[i]);
            }
            for (int i = 0; i < R*16; i++) {
                try {
                    floydfaryHeap.take();
                } catch (PQException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.fill(e5, null);
        });
        double fft5 = ff5.run(false, 200);
        Collections.sort(spilledFloydFary);
        t4[4] = spilledFloydFary.get(spilledFloydFary.size() - 1);
        System.out.println("MaxSpilled: " + t4[0] + ", " + t4[1] + ", " + t4[2] + ", " + t4[3] + ", " + t4[4]);
        System.out.printf("FloydFaryHeap: %.3fms, %.3fms, %.3fms, %.3fms, %.3fms\n\n", fft1, fft2, fft3, fft4, fft5);

        Benchmark_Timer fh1 = new Benchmark_Timer("FibonacciHeap1", b-> {
            FibonacciHeap fabonacciHeap = new FibonacciHeap();
            for (int i = 0; i < I; i++) {
                fabonacciHeap.insert(r[i]);
            }
            for (int i = 0; i < R; i++) {
                fabonacciHeap.extractMin();
            }
        });
        double fht1 = fh1.run(false, 200);

        Benchmark_Timer fh2 = new Benchmark_Timer("FibonacciHeap2", b-> {
            FibonacciHeap fabonacciHeap = new FibonacciHeap();
            for (int i = 0; i < I*2; i++) {
                fabonacciHeap.insert(r[i]);
            }
            for (int i = 0; i < R*2; i++) {
                fabonacciHeap.extractMin();
            }
        });
        double fht2 = fh2.run(false, 200);

        Benchmark_Timer fh3 = new Benchmark_Timer("FibonacciHeap4", b-> {
            FibonacciHeap fabonacciHeap = new FibonacciHeap();
            for (int i = 0; i < I*4; i++) {
                fabonacciHeap.insert(r[i]);
            }
            for (int i = 0; i < R*4; i++) {
                fabonacciHeap.extractMin();
            }
        });
        double fht3 = fh3.run(false, 200);

        Benchmark_Timer fh4 = new Benchmark_Timer("FibonacciHeap8", b-> {
            FibonacciHeap fabonacciHeap = new FibonacciHeap();
            for (int i = 0; i < I*8; i++) {
                fabonacciHeap.insert(r[i]);
            }
            for (int i = 0; i < R*8; i++) {
                fabonacciHeap.extractMin();
            }
        });
        double fht4 = fh4.run(false, 200);

        Benchmark_Timer fh5 = new Benchmark_Timer("FibonacciHeap16", b-> {
            FibonacciHeap fabonacciHeap = new FibonacciHeap();
            for (int i = 0; i < I*16; i++) {
                fabonacciHeap.insert(r[i]);
            }
            for (int i = 0; i < R*16; i++) {
                fabonacciHeap.extractMin();
            }
        });
        double fht5 = fh5.run(false, 200);
        System.out.printf("FibonacciHeap: %.3fms, %.3fms, %.3fms, %.3fms, %.3fms\n\n", fht1, fht2, fht3, fht4, fht5);
    }
}
