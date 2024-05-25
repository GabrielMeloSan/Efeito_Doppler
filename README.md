# Efeito_Doppler

Tudo sendo feito na branch "Testes".

## Simulação do Efeito Doppler com uma Fonte de Ondas Sonoras em Movimento

OBJETIVO: Desenvolver um software que simule o efeito Doppler causado pelo movimento
de uma fonte de ondas sonoras em relação a um observador em repouso, e construir um
banco de dados com os resultados obtidos.

DESCRIÇÃO DA PROPOSTA: Uma fonte produz ondas sonoras com uma determinada
frequência f e potência P, e se encontra em movimento retilíneo uniforme em relação a um
observador em repouso (em relação à Terra) com uma determinada velocidade vf. A fonte
inicialmente está a uma distância x0 do observador e passa a se aproximar dele. Em um
determinado instante de tempo, a distância entre a fonte e o observador atinge o seu valor
mínimo, e a partir desse momento a fonte passa a se afastar dele. Os alunos deverão
desenvolver um software que simule o som percebido por esse observador, levando em
conta:
 As alterações na frequência percebida pelo observador, devido ao Efeito Doppler
 A potência sonora no observador, devido à distância entre ele e a fonte
O software desenvolvido deverá permitir ao usuário variar os valores de:
 Frequência f da onda produzida pela fonte
 Velocidade relativa vf entre a fonte e o observador
 Distância inicial x0 entre a fonte e o observador
 Tempo a ser considerado na simulação (por exemplo, 5 segundos, 10
segundos, etc.)
Os resultados gerados pelo software deverão ser armazenados em um banco de
dados, e o programa deverá gerar um arquivo de áudio (.wav ou .mp3) contendo
os resultados da simulação. Além disso, para a elaboração do software, não será
permitido a utilização de métodos ou funções trigonométricas contidos em
bibliotecas. Em vez disso, para simular as ondas sonoras, os alunos deverão estimar os
valores de seno e/ou cosseno nos instantes de tempo de interesse utilizando as Séries
de Taylor e MacLaurin, vistas em Cálculo Avançado.
