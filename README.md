## Escuela Colombiana de Ingeniería
### Arquitecturas de Software
### Introducción al paralelismo - hilos

### Trabajo individual o en parejas

Entrega: Martes en el transcurso del día.
Entregar: Fuentes y documento PDF con las respuestas.

**Parte I Hilos Java**

1. De acuerdo con lo revisado en las lecturas, complete las clases CountThread, para que las mismas definan el ciclo de vida de un hilo que imprima por pantalla los números entre A y B.
   ![](img/Codigo1.png)
2. Complete el método __main__ de la clase CountMainThreads para que:
	1. Cree 3 hilos de tipo CountThread, asignándole al primero el intervalo [0..99], al segundo [99..199], y al tercero [200..299].
    		![](img/Codigo2.png)
	2. Inicie los tres hilos con 'start()'.
	3. Ejecute y revise la salida por pantalla.      
    		![](img/Start.png)
	4. Cambie el incio con 'start()' por 'run()'. Cómo cambia la salida?, por qué?.
    		![](img/Run.png)

    	La salida cambia ya que al ejecutar los hilos con start estos se estan ejecutando de manera paralela, minetras que, al ejecutarlos por medio de Run() 	los hilos deben esperar a que el anterior termine para poder comenzar con su ejecución.

**Parte II Hilos Java**

La fórmula [BBP](https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula) (Bailey–Borwein–Plouffe formula) es un algoritmo que permite calcular el enésimo dígito de PI en base 16, con la particularidad de no necesitar calcular nos n-1 dígitos anteriores. Esta característica permite convertir el problema de calcular un número masivo de dígitos de PI (en base 16) a uno [vergonzosamente paralelo](https://en.wikipedia.org/wiki/Embarrassingly_parallel). En este repositorio encontrará la implementación, junto con un conjunto de pruebas. 

Para este ejercicio se quiere calcular, en el menor tiempo posible, y en una sola máquina (aprovechando las características multi-core de la mismas) al menos el primer millón de dígitos de PI (en base 16). Para esto

1. Cree una clase de tipo Thread que represente el ciclo de vida de un hilo que calcule una parte de los dígitos requeridos.  
	![](img/CodigoP2.1.png) 
2. Haga que la función PiDigits.getDigits() reciba como parámetro adicional un valor N, correspondiente al número de hilos entre los que se va a paralelizar la solución. Haga que dicha función espere hasta que los N hilos terminen de resolver el problema para combinar las respuestas y entonces retornar el resultado. Para esto, revise el método [join](https://docs.oracle.com/javase/tutorial/essential/concurrency/join.html) del API de concurrencia de Java.  
	![](img/CodigoP2.2.png)
3. Ajuste las pruebas de JUnit, considerando los casos de usar 1, 2 o 3 hilos (este último para considerar un número impar de hilos!)
	![](img/Test.png)

**Parte III Evaluación de Desempeño**

A partir de lo anterior, implemente la siguiente secuencia de experimentos para calcular el millon de dígitos (hex) de PI, tomando los tiempos de ejecución de los mismos (asegúrese de hacerlos en la misma máquina):

1. Un solo hilo.
2. Tantos hilos como núcleos de procesamiento (haga que el programa determine esto haciendo uso del [API Runtime](https://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html)).
3. Tantos hilos como el doble de núcleos de procesamiento.
4. 200 hilos.
5. 500 hilos.

	![](img/ThirdPointResults.png)

Al iniciar el programa ejecute el monitor jVisualVM, y a medida que corran las pruebas, revise y anote el consumo de CPU y de memoria en cada caso. ![](img/jvisualvm.png)

![](img/Results.png)

Con lo anterior, y con los tiempos de ejecución dados, haga una gráfica de tiempo de solución vs. número de hilos. Analice y plantee hipótesis con su compañero para las siguientes preguntas (puede tener en cuenta lo reportado por jVisualVM):
![](img/Grafica.png) 



1. Según la [ley de Amdahls](https://www.pugetsystems.com/labs/articles/Estimating-CPU-Performance-using-Amdahls-Law-619/#WhatisAmdahlsLaw?):

	![](img/ahmdahls.png), donde _S(n)_ es el mejoramiento teórico del desempeño, _P_ la fracción paralelizable del algoritmo, y _n_ el número de hilos, a mayor _n_, mayor debería ser dicha mejora. Por qué el mejor desempeño no se logra con los 500 hilos?, cómo se compara este desempeño cuando se usan 200?.

RTA: Se esperaria que mas hilos deberían mejorar el rendimiento, pero tenemos un límite en cuanto a la ganancia obtenida. Según la Ley de Amdahl, la parte del código que no se puede paralelizar impide que el desempeño mejore indefinidamente con más hilos. Además, al usar 500 hilos en una CPU con menos núcleos, hay más competencia por los recursos y una mayor sobrecarga en la administración de los hilos, lo que frena el rendimiento. En comparación, al usar 200 hilos, se obtiene prácticamente el mismo resultado, porque el exceso de hilos solo genera más cambios de contexto sin aportar beneficios significativos.


2. Cómo se comporta la solución usando tantos hilos de procesamiento como núcleos comparado con el resultado de usar el doble de éste?.
   
RTA: Al aumentar el número de hilos al doble de los núcleos, puede haber una leve mejora en ciertos casos, especialmente si los hilos pasan tiempo esperando operaciones de entrada y salida. Aun así, el beneficio no es lineal y puede verse afectado por la sobrecarga en la gestión de hilos. Si el número de hilos sigue aumentando, llega un punto en el que el rendimiento deja de mejorar e incluso puede empeorar.

4. De acuerdo con lo anterior, si para este problema en lugar de 500 hilos en una sola CPU se pudiera usar 1 hilo en cada una de 500 máquinas hipotéticas, la ley de Amdahls se aplicaría mejor?. Si en lugar de esto se usaran c hilos en 500/c máquinas distribuidas (siendo c es el número de núcleos de dichas máquinas), se mejoraría?. Explique su respuesta.
   
RTA: 
1 hilo por 500 máquinas: Distribuir la carga en múltiples máquinas permitiría aprovechar mejor los recursos individuales sin que haya competencia entre hilos, lo que en teoría haría que la Ley de Amdahl se aplicara de forma más efectiva. Sin embargo, la comunicación entre máquinas introduce latencias que podrían reducir la ganancia esperada.

c hilos en 500/c máquinas: Esta distribución sería más equilibrada, ya que cada máquina utilizaría un número de hilos acorde con sus núcleos, evitando problemas de sobrecarga en una sola CPU. Además, al repartir la carga entre varias máquinas, se optimiza el uso de los recursos y se mantiene un buen desempeño sin generar tiempos muertos o competencia excesiva por los procesadores.



#### Criterios de evaluación.

1. Funcionalidad:
	- El problema fue paralelizado (el tiempo de ejecución se reduce y el uso de los núcleos aumenta), y permite parametrizar el número de hilos usados simultáneamente.

2. Diseño:
	- La signatura del método original sólo fue modificada con el parámetro original, y en el mismo debe quedar encapsulado la paralelización e inicio de la solución, y la sincronización de la finalización de la misma.
	- Las nuevas pruebas con sólo UN hilo deben ser exactamente iguales a las originales, variando sólo el parámetro adicional. Se incluyeron pruebas con hilos adicionales, y las mismas pasan.
	- Se plantea un método eficiente para combinar los resultados en el orden correcto (iterar sobre cada resultado NO sera eficiente).

3. Análisis.
	- Se deja evidencia de la realización de los experimentos.
	- Los análisis realizados son consistentes.
