# Problemas de Pattern Matching - Algoritmo de Knuth-Morris-Prat

## O problema


Dadas duas strings, s1 e s2, verificar a posição da primeira ocorrência de se s2 em s1, se existir.

Assim, se s1 = "ABCDCBDCBDACBDABDCBADF" e s1 = "ADF" o retorno seria 19.


## Enunciado 3

1. O algoritmo de Knuth-Morris-Pratt utiliza um vetor auxiliar (LPS - Longest Proper Prefix) para resolver o problema de busca de padrões em string. O algoritmo está dado abaixo.
 
```javascript
// KMP pattern searching algorithm 

class KMP_String_Matching { 
	void KMPSearch(String pat, String txt) 
	{ 
		int M = pat.length(); 
		int N = txt.length(); 

		// cria lps[] que vai guardar o maior 
		// valor prefixo sufixo para o padrão 
		int lps[] = new int[M]; 
		int j = 0; // index for pat[] 

		// Calcula lps[] 
		computeLPSArray(pat, M, lps); 

		int i = 0; // index for txt[] 
		while (i < N) { 
			if (pat.charAt(j) == txt.charAt(i)) { 
				j++; 
				i++; 
			} 
			if (j == M) { 
				System.out.println("Found pattern "
								+ "at index " + (i - j)); 
				j = lps[j - 1]; 
			} 

			// mismatch após j matches 
			else if (i < N && pat.charAt(j) != txt.charAt(i)) { 
				// Não faz match dos caracteres lps[0..lps[j-1]], 
				// não é necesssário, eles combinarão 
				if (j != 0) 
					j = lps[j - 1]; 
				else
					i = i + 1; 
			} 
		} 
	} 

	void computeLPSArray(String pat, int M, int lps[]) 
	{ 
		// tamanho do maior prefixo sufixo anterior 
		int len = 0; 
		int i = 1; 
		lps[0] = 0; // lps[0] is always 0 

		// loop calcula lps[i] for i = 1 to M-1 
		while (i < M) { 
			if (pat.charAt(i) == pat.charAt(len)) { 
				len++; 
				lps[i] = len; 
				i++; 
			} 
			else // (pat[i] != pat[len]) 
			{ 
				if (len != 0) { 
					len = lps[len - 1]; 
				} 
				else // if (len == 0) 
				{ 
					lps[i] = len; 
					i++; 
				} 
			} 
		} 
	} 

```


1. Implemente o algoritmo acima, para resolver o mesmo problema anterior.
   1. teste-o para strings grandes (>500.000 caracteres). Conte o número de iterações e de instruções.
   1. qual a complexidade, no pior caso?
