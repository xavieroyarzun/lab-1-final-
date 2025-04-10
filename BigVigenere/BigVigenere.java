import java.util.Scanner;

public class BigVigenere {

    private int[] key;
    private char[][] matriz ;
    private char[] cifrado;
    private char[] descrifrado;

    private static int charToPosition(char c) {
        if (c >= 'a' && c <= 'z') return c - 'a';
        if (c >= 'A' && c <= 'Z') return 26 + (c - 'A');
        if (c >= '0' && c <= '9') return 52 + (c - '0');
        return -1;
    }

    private char positionToChar(int pos) {
        if(pos < 26) return (char)('a' + pos);
        if(pos < 52) return (char)('A' + (pos - 26));
        if(pos < 62) return (char)('0' + (pos - 52));
        return pos == 62 ? '[' : ']';
    }

    private int charToValue(char a) {
        if (a >= 'a' && a <= 'z') return a - 'a';
        if (a >= 'A' && a <= 'Z') return 26 + (a - 'A');
        if (a >= '0' && a <= '9') return 52 + (a - '0');

        return -1;
    }

    private char valueToChar(int b) {
        if(b < 26) return (char)('a' + b);
        if(b < 52) return (char)('A' + (b - 26));
        if(b < 62) return (char)('0' + (b - 52));
        return b== 62 ? '[' : ']';
    }

    public  BigVigenere() {
        Scanner key= new Scanner(System.in);
        System.out.print("Ingrese la key  ");
        int numericKey=key.nextInt();

        this.matriz = new char[62][62];
        inicializarMatriz();
    }
    public BigVigenere(String numericKey) {
        char[] keypa = numericKey.toCharArray();
        this.key = new int[keypa.length];

        for(int i = 0; i <keypa.length; i++) {
            this.key[i] = charToPosition(keypa[i]);
        }
        this.matriz = new char[62][62];
        inicializarMatriz();




    }
    private void inicializarMatriz()
    {

        for(int i = 0; i < 62; i++) {
            for(int j = 0; j < 62; j++) {
                this.matriz[i][j] = '0';
            }
        }


        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {

                this.matriz[i][j] = (char) ('a' + (i + j) % 26);
            }
        }

        int cont3=0;

        for(int i=0;i<52;i++) {
            cont3++;
            int cont4=0;
            for(int j=0;j<52;j++) {
                if(matriz[i][j]=='0') {
                    this.matriz[i][j] = (char) ('A' + (cont3-1 + cont4) % 26);
                    cont4++;
                }
            }
        }

        int conta=0;

        for(int i=0;i<62;i++) {
            if(i==52) {
                conta=0;
            }
            conta++;
            int contb=0;
            for(int j=0;j<62;j++) {
                if(matriz[i][j]=='0') {
                    this.matriz[i][j] = (char) ('0' + ((conta-1) + contb) % 10);
                    contb++;
                }
            }
        }



    }



    public String encrypt(String message) {
        char[] messagearr = message.toCharArray();
        cifrado = new char[messagearr.length];

        for (int i = 0; i < messagearr.length; i++) {
            int j = i % key.length;
            int pos = charToPosition(messagearr[i]);
            cifrado[i] = matriz[pos][key[j]];

        }

        return new String(cifrado);
    }

    public String decrypt(String encryptedMessage) {
        char[] encryptedArr = encryptedMessage.toCharArray();
        char[] decrypted = new char[encryptedArr.length];

        for (int i = 0; i < encryptedArr.length; i++) {
            int j = i % key.length;
            int colu = key[j];
            for (int fila = 0; fila < matriz.length; fila++) {
                if (matriz[fila][colu] == encryptedArr[i]) {
                    decrypted[i] = positionToChar(fila);
                    break;
                }
            }
        }
        return new String(decrypted);
    }

    public void reEncrypt() {

        Scanner scanner = new Scanner(System.in);


        System.out.print("Ingrese el mensaje encriptado actual: ");
        String mensajeEncriptado = scanner.nextLine();


        String mensajeDescifrado = this.decrypt(mensajeEncriptado);
        System.out.println("Mensaje descifrado: " + mensajeDescifrado);


        System.out.print("Ingrese la nueva clave: ");
        String nuevaClave = scanner.nextLine();


        char[] keyChars = nuevaClave.toCharArray();
        this.key = new int[keyChars.length];
        for (int i = 0; i < keyChars.length; i++) {
            this.key[i] = charToPosition(keyChars[i]);
        }


        String nuevoMensajeEncriptado = this.encrypt(mensajeDescifrado);


        System.out.println("Nuevo mensaje encriptado: " + nuevoMensajeEncriptado);


    }

    public char search(int position) {
        int cont=0;
        for(int i = 0;i<matriz.length;i++) {
            for(int j=0;j<matriz[i].length;j++) {
                if(position==cont) {
                    return matriz[i][j];
                }
                else {
                    cont++;
                }
            }
        }
        return '\0';
    }

    public char optimalSearch(int position) {
        int fila = position / 62;
        int columna = position % 62;
        return matriz[fila][columna];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Ingrese la clave para el cifrado: ");
        String clave = scanner.nextLine();


        BigVigenere vigenere = new BigVigenere(clave);


        while (true) {
            System.out.println("\nOpciones:");
            System.out.println("1. Cifrar mensaje");
            System.out.println("2. Descifrar mensaje");
            System.out.println("3. Reencriptar con nueva clave");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) {
                System.out.print("Ingrese el mensaje a cifrar: ");
                String mensaje = scanner.nextLine();
                String cifrado = vigenere.encrypt(mensaje);
                System.out.println("Mensaje cifrado: " + cifrado);




            }
            else if (opcion == 2) {
                System.out.print("Ingrese el mensaje a descifrar: ");
                String mensajeCifrado = scanner.nextLine();
                String descifrado = vigenere.decrypt(mensajeCifrado);
                System.out.println("Mensaje descifrado: " + descifrado);
            }
            else if (opcion == 3) {
                vigenere.reEncrypt();
            }
            else if (opcion == 4) {
                System.out.println("Saliendo del programa...");
                scanner.close();
                return;
            }
            else {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
}