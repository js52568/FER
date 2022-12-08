#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <time.h>
#include <errno.h>
#include <string.h>

struct my_msgbuf {
    long mtype;
    char mtext[200];
};

int msqid;

void papirPusac () {
	struct my_msgbuf buf;
    	key_t key;

    	key = 54321;
    
    	if ((msqid = msgget(key, 0600 | IPC_CREAT)) == -1) { /* connect to the queue */
        	perror("msgget");
        	exit(1);
    	}
    
    	for(;;) { /* Spock never quits to his captain! */
        	if (msgrcv(msqid, (struct msgbuf *)&buf, sizeof(buf)-sizeof(long), 0, 0) == -1) {
            		perror("msgrcv");
            		exit(1);
        	}
        	if (buf.mtype == 1) {
        	printf("Papir pusac: received: \"%s\". \nPapir pusac:Uzet cu!\n", buf.mtext);
        	}
    }

	}
	
void duhanPusac () {
	struct my_msgbuf buf;
    	key_t key;

    	key = 54321;
    
    	if ((msqid = msgget(key, 0600 | IPC_CREAT)) == -1) { /* connect to the queue */
        	perror("msgget");
        	exit(1);
    	}
    
    	for(;;) { /* Spock never quits to his captain! */
        	if (msgrcv(msqid, (struct msgbuf *)&buf, sizeof(buf)-sizeof(long), 0, 0) == -1) {
            		perror("msgrcv");
            		exit(1);
        	}
        	if (buf.mtype == 2) {
        	printf("Duhan pusac: received: \"%s\". \nDuhan pusac:Uzet cu!\n", buf.mtext);
        	}
	}
	}
	
void sibicePusac () {
	struct my_msgbuf buf;
    	key_t key;

    	key = 54321;
    
    	if ((msqid = msgget(key, 0600 | IPC_CREAT)) == -1) { /* connect to the queue */
        	perror("msgget");
        	exit(1);
    	}
    
    	for(;;) { /* Spock never quits to his captain! */
        	if (msgrcv(msqid, (struct msgbuf *)&buf, sizeof(buf)-sizeof(long), 0, 0) == -1) {
            		perror("msgrcv");
            		exit(1);
        	}
        	if (buf.mtype == 3) {
        	printf("Sibice pusac: received: \"%s\". \nSibice pusac:Uzet cu!\n", buf.mtext);
        	}
	}
}

void trgovac () {
	struct my_msgbuf buf;
    	int msqid;
    	key_t key;
    	
    	while(1){
    	int num = rand() % 4;
    	char text[100];
    	switch(num) {
    		case 1:
    			strcpy(text,"Trgovac:Stavio sam duhan i sibice!\n");
    		case 2:
    			strcpy(text,"Trgovac:Stavio sam papir i sibice!\n");
    		case 3:
    			strcpy(text,"Trgovac:Stavio sam duhan i papir!\n");
    			}

    	key = 54321;
    
    	if ((msqid = msgget(key, 0600 | IPC_CREAT)) == -1) {
    		perror("msgget");
		exit(1);
    	}
    	memcpy(buf.mtext, text, strlen(text)+1);
    	
    	buf.mtype = num;
    	if (msgsnd(msqid, (struct msgbuf *)&buf, strlen(text)+1, 0) == -1)
            	perror("msgsnd");
    	switch(num) {
    		case 1:
    			printf("Trgovac:Stavio sam duhan i sibice!\n");
    		case 2:
    			printf("Trgovac:Stavio sam papir i sibice!\n");
    		case 3:
    			printf("Trgovac:Stavio sam duhan i papir!\n");
    			
    	}
    	sleep(2);
	}
	}




int main(void) {
	switch(fork()) {
		case 0:
			printf("Trgovac je za stolom\n");
			trgovac();
			exit(0);
		case -1:
			fprintf(stderr, "Greska");
	}
	switch(fork()) {
		case 0:
			printf("Pusac s papirom je za stolom\n");
			papirPusac();
			exit(0);
		case -1:
			fprintf(stderr, "Greska");
	}
	switch(fork()) {
		case 0:
			printf("Pusac s duhanom je za stolom\n");
			duhanPusac();
			exit(0);
		case -1:
			fprintf(stderr, "Greska");
	}
	switch(fork()) {
		case 0:
			printf("Pusac sa sibicama je za stolom\n");
			sibicePusac();
			exit(0);
		case -1:
			fprintf(stderr, "Greska");
	}
	for (int i=0; i<4; i++) {
		wait(NULL);
	}
	
	return 0;
}