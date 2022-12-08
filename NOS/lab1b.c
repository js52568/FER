#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <string.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>
#include <sys/msg.h>
#define _XOPEN_SOURCE 500
#include <signal.h>
#define MAXREAD 200


int N;

int ShmId;
struct data {
    int id;
    int logsat;
    int br;
};

struct message {
 int type;
 int id;
 int c;
};


struct data *baza;

void proces (int index) {
	struct data pdata;
	pdata.id = index;
	pdata.logsat = 0;
	pdata.br = 0;
	baza[index] = pdata;
	
	int pfd[2];
	char buf[MAXREAD] = "";
	struct message m;/* poruka*/
	m.type = 0;
	m.id = pdata.id;
	m.c = pdata.logsat;
	
	if (pipe(pfd) == -1)/* stvaranje cjevovoda*/

		exit(1);

	switch (fork()) {

		case -1: /* dijete nije kreirano*/

			exit(1);

		case 0:/* dijete čita */

			close(pfd[1]);/* zato zatvara kraj za pisanje*/
			(void) read(pfd[0], buf, MAXREAD);
			puts(buf);
			exit(0);

		default:/* roditelj piše */

			close(pfd[0]);/* zato zatvara kraj za čitanje*/
			(void) write(pfd[1], m, strlen(m) + 1);
			wait(NULL);/* roditelj čeka da dijete završi*/

	}
	exit(0);/* zatvara sve deskriptore */
	}

int main(void) {
	struct data pdata;
	N = 3;
	
	if (N<3 | N>10) {
		perror("N out of range");
		exit(1);
	}
	
	ShmId = shmget (IPC_PRIVATE, sizeof(pdata) * N, 0600);
	
	if (ShmId == -1)
		exit (1);
		
	baza = (struct data *) shmat (ShmId, NULL, 0);
		
	int pid;
	
	for (int i=0; i < N; i++) {
		switch (pid = fork()) {
		
		case -1: exit(1);
		
		case 0: 
			proces(i);
			exit(0);
		
		default: break;
		}
	}
	for (int i=0; i<N; i++)
		wait(NULL);
	printf ("baza=");
	for (int i = 0; i < N; i++)
		printf ("%d ", baza[i].id);
	shmctl (ShmId, IPC_RMID, NULL);
	
	return 0;
}
