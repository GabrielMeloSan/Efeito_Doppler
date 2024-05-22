//retirado a parte package

public class SenoECosseno {

    //Função para calcula fatorial
    public static int calcularFatorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return n * calcularFatorial(n - 1);
        }
    }

      // Função que calcula o seno de um ângulo em RADIANOS
      public double CalculaSeno(double x) {
        double precisao = 1e-15;
        double estimativa = 0;
        int k = 0;

        // Normaliza o valor do ângulo para o intervalo [0, 2π)
        x = x % (2 * Math.PI);
        if (x < 0) {
            x += 2 * Math.PI;
        }

        // Se o valor for maior que π, ajusta para o intervalo [-π, π]
        if (x > Math.PI) {
            x -= 2 * Math.PI;
        }

        // Se o valor for maior que π/2 ou menor que -π/2, usa a identidade seno
        if (x > Math.PI / 2) {
            x = Math.PI - x;
        } else if (x < -Math.PI / 2) {
            x = -Math.PI - x;
        }

        // Série de Maclaurin para seno
        double termo = x;  // Primeiro termo da série
        while (Math.abs(termo) > precisao) {
            estimativa += termo;
            k++;
            termo = -termo * x * x / (2 * k * (2 * k + 1));
        }

        return estimativa;
    }
        
    }
