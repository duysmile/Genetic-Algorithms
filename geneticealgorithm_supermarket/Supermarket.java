/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticealgorithm_supermarket;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author duy21
 */
public class Supermarket {
        public String[] FoodName = { "Tao", "Cherry", "Cam", "Quyt", "Duahau", "Chanh", "Kiwi", "Nho", "DuaLuoi", "DaoTien",
			"Chuoi", "DauTay", "Thom", "Le", "SuaBo", "CaChua", "CaRot", "CaTim", "HanhTay", "Toi", "KhoaiTay", "Nam",
			"BanhMy", "PhoMai", "Bia", "Ngheu", "Cua", "Ca", "Tom", "Ga", "Bo", "Trung" };
	public int[][] Food = { { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 1, 1, 0, 0 },
			{ 0, 0, 1, 0, 0, 0, 0, 0, 1, 1 }, { 0, 0, 1, 1, 1, 0, 0, 0, 0, 1 }, { 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 1, 0, 0, 1 }, { 0, 0, 1, 1, 1, 0, 0, 1, 0, 1 }, { 0, 0, 1, 0, 0, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 1, 0, 1, 0 }, { 0, 0, 1, 1, 0, 1, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 1, 0, 0, 0, 1 },
			{ 0, 0, 1, 1, 0, 0, 1, 0, 0, 1 }, { 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 }, { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 0, 1, 1, 0, 1, 0, 0 }, { 0, 0, 1, 0, 0, 1, 0, 1, 0, 1 }, { 0, 0, 1, 1, 0, 0, 1, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 }, { 0, 0, 1, 0, 1, 1, 0, 0, 0, 0 }, { 1, 0, 0, 1, 0, 0, 1, 0, 1, 1 },
			{ 0, 0, 1, 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 0, 0, 1, 1, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 0, 0, 1, 0 },
			{ 1, 0, 0, 0, 1, 1, 0, 0, 0, 0 }, { 1, 1, 0, 0, 0, 0, 0, 0, 1, 0 }, { 1, 0, 0, 0, 1, 0, 1, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 1, 0, 1, 0, 0 }, { 1, 1, 0, 0, 0, 1, 0, 0, 0, 0 }, { 1, 1, 0, 0, 1, 1, 0, 1, 0, 0 } };

	public int[] FoodValue = { 31, 66, 55, 70, 34, 50, 106, 38, 62, 49, 55, 82, 33, 13, 118, 54, 83, 15, 56, 123, 32,
			40, 52, 63, 18, 74, 49, 62, 64, 55, 70, 100 };
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Supermarket();
    }
    int N = 100; //so lan di truyen
    int [][] population = new int [N][32];
    int [] evaluate = new int [N]; //mang danh gia
    Random rand = new Random();
    public Supermarket(){
        InitialPopulation(); // Khoi tao
        for (int i = 0; i < 100; i++) {
            FitnessFunction(); // Danh gia
            Print();
            Selection(); //Chon loc
            Crossover(); //Lai ghep
            Mutation(); //Dot bien
        }
    }

    private void InitialPopulation() {
        for(int i = 0; i < N; i++){
            for(int j = 0; j < 32; j++){
                population[i][j] = rand.nextInt(2);
            }
        }
    }

    private void Print() {
        int [] tmp = evaluate.clone();
        Arrays.sort(tmp);
        int best = tmp[0];
        for(int i = 0; i < N; i++){
            if (evaluate[i] == best){
                int totalAmount = 0; //tong so tien
                int [] nutrition = new int[10]; // mang chat dinh duong
                int totalNutrition = 0; //tong so chat dinh duong
                for(int j = 0; j < 32; j++){
                    if (population[i][j] == 1){
                        System.out.print(FoodName[j] + ", ");
                        totalAmount += FoodValue[j];
                        for(int k = 0; k < 10; k++){
                            if (Food[j][k] == 1){
                                nutrition[k] = 1;
                            } 
                        }
                    }
                }
                for (int j = 0; j < 10; j++){
                    if (nutrition[j] == 1) totalNutrition ++;
                }
                System.out.print(". Tong so tien: " + totalAmount);
                System.out.print(". Tong so dinh duong: " + totalNutrition);
                System.out.println();
                break;
            }
        }
    }

    private void FitnessFunction() {
        for(int i = 0; i < N; i++){
                int totalAmount = 0; //tong so tien
                int [] nutrition = new int[10]; // mang chat dinh duong
                int totalNutrition = 0; //tong so chat dinh duong
                for(int j = 0; j < 32; j++){
                    if (population[i][j] == 1){
                        totalAmount += FoodValue[j];
                        for(int k = 0; k < 10; k++){
                            if (Food[j][k] == 1){
                                nutrition[k] = 1;
                            } 
                        }
                    }
                }
                for (int j = 0; j < 10; j++){
                    if (nutrition[j] == 1) totalNutrition ++;
                }
            evaluate[i] = totalAmount - totalNutrition * 50; //uoc luong danh gia cho moi nghiem (gia thanh re va day du dinh duong)
        }
    }

    private void Selection() {
        int [] tmp = evaluate.clone();
        Arrays.sort(tmp);
        int threshold = tmp[N * 80 / 100]; //nguong dung chon loc (trung binh la 80%)
        for (int i = 0; i < N; i++){
            if (evaluate[i] >= threshold){
                population[i] = population[rand.nextInt(N)].clone();
            }
        }
    }

    private void Crossover() {
        for (int i = 0; i < N / 30 * 100; i++){
            int father = rand.nextInt(N);
            int mother = rand.nextInt(N);
            for (int j = 0; j < 32; j++){
                if(rand.nextBoolean()){
                    int tmp = population[father][j];
                    population[father][j] = population[mother][j];
                    population[mother][j] = tmp;
                }
            }
        }
    }
    
    private void Mutation() {
        int random = rand.nextInt(N);
        population[random][rand.nextInt(32)] = 1 - population[random][rand.nextInt(32)];
    }
}
