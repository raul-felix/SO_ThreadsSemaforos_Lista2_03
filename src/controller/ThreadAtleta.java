package controller;

import java.util.concurrent.Semaphore;

public class ThreadAtleta extends Thread {

	static int pontuacao = 250;
	
	int id;
	Semaphore semaforoTiro;
	Semaphore semaforoPontuacao;
	int pontos;
	
	public ThreadAtleta(int id, Semaphore semaforoTiro, Semaphore semaforoPontuacao) {
		this.id = id;
		this.semaforoTiro = semaforoTiro;
		this.semaforoPontuacao = semaforoPontuacao;
		pontos = 0;
	}

	@Override
	public void run() {
		corrida();
		
		try {
			semaforoTiro.acquire();
			tiroAoAlvo();
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		} finally {
			semaforoTiro.release();
		}
		
		ciclismo();
		
		try {
			semaforoPontuacao.acquire();
			pontos += pontuacao;
			pontuacao -= 10;
			System.out.println(String.format("Atleta #%d: %d pontos!", id, pontos));
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		} finally {
			semaforoPontuacao.release();
		}
	}
	
	private void corrida() {
		int distancia = 3000;
		int distPercorrida = 0;
		int tempo = 30;
		
		while (distPercorrida < distancia) {
			int passo = (int) (Math.random() * (25 - 20 + 1) + 20);
			distPercorrida += passo;
			try {
				sleep(tempo);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	private void tiroAoAlvo() {
		for (int i = 0; i < 3; i++) {
			int tempo = (int) (Math.random() * (3000 - 500 + 1) + 500);
			int pontoTiro = (int) (Math.random() * 11);
			pontos += pontoTiro;
			try {
				sleep(tempo);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	private void ciclismo() {
		int distancia = 5000;
		int distPercorrida = 0;
		int tempo = 40;
		
		while (distPercorrida < distancia) {
			int passo = (int) (Math.random() * (40 - 30 + 1) + 30);
			distPercorrida += passo;
			try {
				sleep(tempo);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
}
