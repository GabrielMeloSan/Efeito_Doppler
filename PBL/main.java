//arquivo de testes


import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        SenoECosseno calc = new SenoECosseno();

        System.out.println("Inserir valor de seno e cosseno em rad: ");

        Scanner scn = new Scanner(System.in);

        double x = scn.nextDouble();

        //precisao arbitraria de 0.0001
        System.out.println("Seno: " + Double.toString(calc.CalculaSeno(x, 0.0001)));
        System.out.println("Coseno: " + Double.toString(calc.CalculaCosseno(x, 0.0001)));
    }
}
