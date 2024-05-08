package PBL;

public class SenoECosseno {

    //Função para calcula fatorial
    public static int calcularFatorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return n * calcularFatorial(n - 1);
        }
    }

    //Função que calculo o seno de um ângulo em RADIANOS
    public double CalculaSeno(double x, double PrecisaoDesejada){
//declaração de variaveis
        double estimativa = 0;
        int k = 0;

        //deixando o valor de x entre -2pi e 2pi
        x = x % (2 * Math.PI);

        //valor de erro
        double ValorDeErro = (Math.pow(x, (k+1)))/calcularFatorial(k+1);
        
        //contador para auxiliar no segundo ciclo de repetição
        int contador = 0;

        //Ciclo de repetição para encontrar o valor de k
        while (ValorDeErro >= PrecisaoDesejada){
            k+=1;
            ValorDeErro = ((Math.pow(x, (k+1)))/calcularFatorial(k+1));
        }

        //Ciclo de repetição para encontrar o valor da estimativa baseado na quantidade de iterações
        while(contador < k){

            //Desvio condicional para as somas da série de MacLaurin
            if(contador%2 != 0 && (contador+1)%4 != 0){
                //Calculo do valor
                estimativa = estimativa + (Math.pow(x, contador)/calcularFatorial(contador));
            }

            //Desvio condicional para as subtrações da série de MacLaurin
            if(contador%2 != 0 && (contador+1)%4 == 0){
                //Calculo do valor
                estimativa = estimativa - (Math.pow(x, contador)/calcularFatorial(contador));
            }

            contador++;
        }

        return estimativa;
    }

        //Função que calculo o Cosseno de um ângulo em RADIANOS
    public double CalculaCosseno(double x, double PrecisaoDesejada){
    //declaração de variaveis
        double estimativa = 0;
        int k = 0;

        //deixando o valor de x entre -2pi e 2pi
        x = x % (2 * Math.PI);

        //valor de erro
        double ValorDeErro = (Math.pow(x, (k+1)))/calcularFatorial(k+1);
        
        //contador para auxiliar no segundo ciclo de repetição
        int contador = 0;

        //Ciclo de repetição para encontrar o valor de k
        while (ValorDeErro >= PrecisaoDesejada){
            k+=1;
            ValorDeErro = ((Math.pow(x, (k+1)))/calcularFatorial(k+1));
        }

        //Ciclo de repetição para encontrar o valor da estimativa baseado na quantidade de iterações
        while(contador < k){

            //Desvio condicional para as somas da série de MacLaurin
            if(contador%2 == 0 && (contador)%4 == 0){
                //Calculo do valor
                estimativa = estimativa + (Math.pow(x, contador)/calcularFatorial(contador));
            }

            //Desvio condicional para as subtrações da série de MacLaurin
            if(contador%2 == 0 && (contador)%4 != 0){
                //Calculo do valor
                estimativa = estimativa - (Math.pow(x, contador)/calcularFatorial(contador));
            }

            contador++;
        }
        return estimativa;
    }
}