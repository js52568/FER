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
#define _XOPEN_SOURCE 500
#include <signal.h>

struct my_msgbuf {
    long mtype;
    char mtext[200];
};

int trgovacid;
int papirid;
int duhanid;
int sibiceid;

void retreat(int failure) 
{
    if (msgctl(trgovacid, IPC_RMID, NULL) == -1) {
        perror("msgctl");
        exit(1);
    }
    if (msgctl(papirid, IPC_RMID, NULL) == -1) {
        perror("msgctl");
        exit(1);
    }
    if (msgctl(duhanid, IPC_RMID, NULL) == -1) {
        perror("msgctl");
        exit(1);
    }
    if (msgctl(sibiceid, IPC_RMID, NULL) == -1) {
        perror("msgctl");
        exit(1);
    }
    exit(0);
}

int main(void)
{
    printf("Trgovac je za stolom\n");
    printf("Pusac s papirom je za stolom\n");
    printf("Pusac s duhanom je za stolom\n");
    printf("Pusac s sibicama je za stolom\n");
    struct my_msgbuf buf;
    int msqid;
    key_t key;
    
    for(;;){
    		
	    int num = (rand() % (3) + 1);
    	    char text[100];
    	    switch(num) {
    		case 1:
    			strcpy(text,"Trgovac:Stavio sam duhan i sibice!\n");
    			msqid = papirid;
    			break;
    		case 2:
    			strcpy(text,"Trgovac:Stavio sam papir i sibice!\n");
    			msqid = duhanid;
    			break;
    		case 3:
    			strcpy(text,"Trgovac:Stavio sam duhan i papir!\n");
    			msqid = sibiceid;
    			break;
    			}

	    key = num*10000;
	    if ((msqid = msgget(key, 0600 | IPC_CREAT)) == -1) {
		perror("msgget");
		exit(1);
	    }
	    
	    memcpy(buf.mtext, text, strlen(text)+1);
	    buf.mtype = num;
	    
	    if (msgsnd(msqid, (struct msgbuf *)&buf, strlen(text)+1, 0) == -1){
		    perror("msgsnd");
		    }
	    switch(num) {
	    		case 1:
	    			printf("Trgovac:Stavio sam duhan i sibice!\n");
	    			break;
	    		case 2:
	    			printf("Trgovac:Stavio sam papir i sibice!\n");
	    			break;
	    		case 3:
	    			printf("Trgovac:Stavio sam duhan i papir!\n");
	    			break;
	    			
	    	}
	    key = 54321;
    
	    if ((trgovacid = msgget(key, 0600 | IPC_CREAT)) == -1) { 
		perror("msgget");
		exit(1);
	    }
	    
	    sigset(SIGINT, retreat);
	    if (msgrcv(trgovacid, (struct msgbuf *)&buf, sizeof(buf)-sizeof(long), 4, 0) == -1) {
            	perror("msgrcv");
            	exit(1);
        	}
        	
            printf("Trgovac: received: \"%s\". \nUzivaj!\n", buf.mtext);
	    sleep(1);
    }
    
    return 0;
}
