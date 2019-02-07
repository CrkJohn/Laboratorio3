# Black List Search and Snake Race
Arquitectura de software


![GitHub last commit](https://img.shields.io/github/last-commit/CrkJohn/Laboratorio3.svg?style=for-the-badge)

**Lenguaje manejado.**     ![90+%]( https://img.shields.io/github/languages/top/crkJohn/Laboratorio3.svg?style=for-the-badge&colorB=blue)

###### Integrantes
- [John David Ibañez](https://github.com/CrkJohn)
- [Juan Camilo Velandia](https://github.com/jcamilovelandiab)
------------

En el presente trabajo se adjuntan fotos que comprueban la realización de los ejercicios propuestos en éste laboratorio.

## Black List
#### Part I - Introduction to threads in JAVA
2. Complete the ```main``` method of the ```CountMainThreads``` class so that: 

```java
public class CountMainThreads {
	public static void main(String args[]) {
		Count cnt = new Count(0,99) , cnt2 = new Count(99,199) , cnt3  = new Count(200,299);
		try {
			while (cnt.isAlive()) {
				System.out.println("Main thread1 will be alive till the child thread is live");
				cnt.run();
				Thread.sleep(1500);
			}
			while (cnt2.isAlive()) {
				System.out.println("Main thread2 will be alive till the child thread is live");
				cnt2.run();
				Thread.sleep(1500);
			}
			while (cnt3.isAlive()) {
				System.out.println("Main thread3 will be alive till the child thread is live");
				cnt3.run();
				Thread.sleep(1500);
			}
		} catch (InterruptedException e) {
			System.out.println("Main thread interrupted");
		}
		System.out.println("Main thread's run is over");
	}
}
```
![](https://github.com/CrkJohn/Laboratorio3/blob/master/Foticos/PartI-2.PNG)
![](https://github.com/CrkJohn/Laboratorio3/blob/master/Foticos/PartI-2.2.PNG)


3. Change the beginning with ```start()```  to ```run()```. How does the output change? Why?
fotos
Lo que sucedio fue que al ejecutar el metodo ```start()``` asigna un nuevo subproceso independiente a la ejecución del proceso principal, el meotodo ```run()```  es el que ejecuta el hilo y el que sabe que va hacer el hilo, al no tener preparado el hilo todo  se va ejecutar secuencialmente.

![](https://github.com/CrkJohn/Laboratorio3/blob/master/Foticos/PartI-3.PNG)

#### Part II - Black List Search Exercise
1. Create a  ```Thread ``` class that represents the life cycle of a thread that searches for a segment of the pool of available servers. Add to that class a method that allows you to **ask** the instances of it (the threads) how many occurrences of malicious servers it has found or found.

 [Solucion](https://github.com/CrkJohn/Laboratorio3/blob/master/src/main/java/edu/eci/arsw/threads/LifeCycleThread.java)
 
2. Add to the   ```checkHost  ``` method an integer parameter   ```N```, corresponding to the number of threads between which the search will be carried out (remember to take into account if N is even or odd!). Modify the code of this method so that it divides the search space between the indicated N parts, and parallels the search through N threads... 

[Solucion ](https://github.com/CrkJohn/Laboratorio3/blob/master/src/main/java/edu/eci/arsw/blacklistvalidator/HostBlackListsValidator.java)

*resultados*
```java
List<Integer> blackListOcurrences=hblv.checkHost("202.24.24.55",14);
System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
```
![](https://github.com/CrkJohn/Laboratorio3/blob/master/Foticos/resultadoBlackListVacia.PNG) 
```java 
List<Integer> blackListOcurrences=hblv.checkHost("202.24.34.55",14);
System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
 ```
 
 ![](https://github.com/CrkJohn/Laboratorio3/blob/master/Foticos/resultadoBlackListLlena.PNG)


#### Part III - Discussion
- Un contador global para los ```N``` hilos, donde este se instancia en contralador y asi logrando que en el ```run```  del contralador ir verficando el valor que se lleva.
- El problema que se tendria seria  interleaving pero sincronizando los hilos y manejando una cola esperados se podria solucionar este problema.

#### Part IV - Performance Evaluation 
1.
![](https://github.com/CrkJohn/Laboratorio3/blob/master/Foticos/1Thread.PNG)
2.
![](https://github.com/CrkJohn/Laboratorio3/blob/master/Foticos/2Thread.PNG)
3.
![](https://github.com/CrkJohn/Laboratorio3/blob/master/Foticos/3Thread.PNG)
4.
![](https://github.com/CrkJohn/Laboratorio3/blob/master/Foticos/50Thread.PNG)
5. 
![](https://github.com/CrkJohn/Laboratorio3/blob/master/Foticos/100Thread.PNG)
6. El rendimiento de un programa no se da por aumentar la cantidad de hilos si no por la cantidad de cores que tiene la maquina, así sabiendo que cada proceso va tener un maximo donde la funcion de la aceleracion del programa se estabilizara , pero al evaluar n en S(n)
termina siendo mas eficiente pero no con mejor rendimiento.

7. El numero de cores y el dos veces el numero de cores tiene el mismos comportamiento.


