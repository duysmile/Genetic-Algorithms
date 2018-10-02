/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seller;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author DuyN
 * Bai toan nguoi du lich
 */
public class Seller {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Seller();
    }
    
    int N = 100; // tong so nghiem trong mot lan di truyen (tien hoa)
    int cityAmount = 100;
    int [][] population = new int [N][cityAmount];
    int [][] distance = new int [cityAmount][cityAmount];
    int [] evaluate = new int [N];
    Random rand = new Random();
    
    public Seller(){
        randomInit();
        InitialPopulation();
        for (int i = 0; i < 100; i++){
            FitnessFunction();
            Print();
            Selection();
            Crossover();
            Mutation();
        }
    }
    
    //random define N city and distance
    public void randomInit(){
        for (int i = 0; i < cityAmount; i++) {
            for (int j = 0; j < cityAmount; j++) {
                if (i == j){
                    distance[i][j] = 0;
                } else if (j < i){
                    distance[i][j] = distance[j][i];
                } else {
                    distance[i][j] = rand.nextInt(5000) + 500;
                }
            }
        }
    }
    public void InitialPopulation(){
        for (int i = 0; i < N; i++){
            population[i] = Permutation(cityAmount);
        }
    }

    private void FitnessFunction() {
        for (int i = 0; i < N; i++){
            int totalDistance = 0;
            for (int j = 0; j < cityAmount; j++){
                totalDistance += distance[population[i][j] - 1][population[i][(j + 1) % 100] - 1];
            }
            evaluate[i] = totalDistance;
        }
    }

    private void Print() {
        int [] tmp = evaluate.clone();
        Arrays.sort(tmp);
        int best = tmp[0];
        for (int i = 0; i < N; i++){
            if (evaluate[i] == best){
                for (int j = 0; j < cityAmount; j++){
//                    System.out.print(population[i][j]); 
                    if (j < cityAmount - 1){
//                        System.out.print(" - ");
                    }
                }
//                System.out.println(".");
//                System.out.println("Total Distance: " + best);
                System.out.println(best);
                break;
            }
        }
    }

    private void Selection() {
        int [] tmp = evaluate.clone();
        Arrays.sort(tmp); // sort ascending
        int threshold = tmp[N * 80 / 100];
        for (int i = 0; i < N; i++){
            if (evaluate[i] >= threshold){
                population[i] = population[rand.nextInt(cityAmount)].clone();
            }
        }
    }
    
    private int[] CrossoverDetail(int [] father, int [] mother){
        int []tmp = new int[cityAmount];
        int position = rand.nextInt(cityAmount);
        int x = 0;
        int [] inProgress = new int[cityAmount];
        while (x < cityAmount){
            if (x <= position) {
                tmp[x] = father[x];
                inProgress[father[x] - 1] = 1;
            } else {
                if (inProgress[mother[x] - 1] == 1){
                    tmp[x] = -1;
                } else {
                    tmp[x] = mother[x];
                }
            }
            x++;
        }

        int j = 0;
        int k = 0;
        while (j < cityAmount && k < cityAmount){
            while (k < cityAmount && inProgress[k] != 0) k++;
            while (j < cityAmount && tmp[j] != -1) {
                j++;
            }
            if (j >= cityAmount){
                break;
            }
            tmp[j] = k + 1;
            j++;
            k++;
        }
        return tmp;
    }

    private void Crossover() {
        for (int i = 0; i < N * 30 / 100; i++){
            int father = rand.nextInt(N);
            int mother = rand.nextInt(N);
            int [] tmpFather = population[father].clone();
            int [] tmpMother = population[mother].clone();
            population[father] = CrossoverDetail(tmpFather, tmpMother);
            population[mother] = CrossoverDetail(tmpMother, tmpFather);
        }
    }

    private void Mutation() {
        int position = rand.nextInt(N);
        int x = rand.nextInt(cityAmount);
        int y = rand.nextInt(cityAmount);
        int tmp = population[position][x];
        population[position][x] = population[position][y];
        population[position][y] = tmp;
    }

    private int[] Permutation(int cityAmount) {
        int [] tmp = new int[cityAmount];
        for (int i = 0; i < cityAmount; i++){
            tmp[i] = i + 1;
        }
        for (int i = 0; i < cityAmount; i++){
            int x = rand.nextInt(cityAmount);
            int y = rand.nextInt(cityAmount);
            int temp = tmp[x];
            tmp[x] = tmp[y];
            tmp[y] = temp;
        }
        return tmp;
    }
}
