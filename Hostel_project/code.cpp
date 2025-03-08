#include <iostream>
#include <cstdlib>
#include <ctime>
#include <windows.h>
using namespace std;

struct date {
    int day;
    int month;
    int year;
};

class Student {
public:
    string name;
    string status;
    int room;
    int rollNo;
    int appId;
    date dateLeave;
    date dateArrive;

    void getData();
};
struct sdata{
    Student *s[100];
    int id[100];
};
struct BSTree {
    int roomNo;
    Student student1;
    Student student2;
    BSTree* left;
    BSTree* right;

    BSTree(int roomNo, string name1, int rollNo1, string name2, int rollNo2)
        : roomNo(roomNo), left(nullptr), right(nullptr) {
        student1.name = name1;
        student1.rollNo = rollNo1;
        student2.name = name2;
        student2.rollNo = rollNo2;
    }
};

BSTree* root = nullptr;

BSTree* insertNode(BSTree* node, int roomNo, string name1, int rollNo1, string name2, int rollNo2) {
    if (node == nullptr) {
        return new BSTree(roomNo, name1, rollNo1, name2, rollNo2);
    }
    if (roomNo < node->roomNo) {
        node->left = insertNode(node->left, roomNo, name1, rollNo1, name2, rollNo2);
    } else {
        node->right = insertNode(node->right, roomNo, name1, rollNo1, name2, rollNo2);
    }
    return node;
}

BSTree* searchNode(BSTree* node, int roomNo) {
    if (node == nullptr || node->roomNo == roomNo) {
        return node;
    }
    if (roomNo < node->roomNo) {
        return searchNode(node->left, roomNo);
    }
    return searchNode(node->right, roomNo);
}
struct sdata data;
int i=0;
void Student::getData() {
    cout << "Enter room number: ";
    cin >> room;
    BSTree* node = searchNode(root, room);
    if (node == nullptr) {
        cout << "Invalid room number" << endl;
        return;
    }

    int choice;
    cout << "1. " << node->student1.name << endl;
    cout << "2. " << node->student2.name << endl;
    cout << "Enter name (1/2): ";
    cin >> choice;

    Student* selectedStudent = (choice == 1) ? &node->student1 : &node->student2;
    selectedStudent->room=room;
    cout << "Name: " << selectedStudent->name << endl;
    cout << "Roll number: " << selectedStudent->rollNo << endl;
    selectedStudent->status = "Pending";
    cout << "Enter date of leaving (DD MM YYYY): ";
    cin >> selectedStudent->dateLeave.day >> selectedStudent->dateLeave.month >> selectedStudent->dateLeave.year;
    cout << "Enter date of arrival (DD MM YYYY): ";
    cin >> selectedStudent->dateArrive.day >> selectedStudent->dateArrive.month >> selectedStudent->dateArrive.year;

    srand(time(nullptr));
    selectedStudent->appId = rand() % 100;
    cout<<"Application id:"<<selectedStudent->appId<<endl;
    cout<<"Applied succesfully"<<endl;
    cout<<endl;
    data.id[i]=selectedStudent->appId;
    data.s[i]=selectedStudent;
    i++;
}

void printData(int n) {
   // HANDLE h = GetStdHandle(STD_OUTPUT_HANDLE);
    int i,x,f=0;Student*t;
    cout<<"\n\n\n";
    for(i=0;i<n;i++)
    {
        t=data.s[i];
        if(t->status=="Pending")
        {
            f=1;
        cout<<"Application Id:"<<data.id[i]<<endl;
        cout<<"Name:"<<t->name<<endl;
        cout<<"Room no:"<<t->room<<endl;
        cout << "Date of leaving: " << data.s[i]->dateLeave.day << "/" << data.s[i]->dateLeave.month << "/" << data.s[i]->dateLeave.year << endl;
        cout << "Date of arrival: " << data.s[i]->dateArrive.day << "/" << data.s[i]->dateArrive.month << "/" << data.s[i]->dateArrive.year << endl;
        if (t->status == "Pending") {
            SetConsoleTextAttribute(h, 14);
        }
        else if (t->status == "Approved") {
                SetConsoleTextAttribute(h, 2);
        }
        else {
                SetConsoleTextAttribute(h, 4);
        }
        cout << t->status << endl;
        SetConsoleTextAttribute(h, 7);
        cout<<"Enter your decision:\n1.Accept\n2.Deny:";
        cin>>x;
        if(x==1)
        {
            t->status="Approved";
        }
        else if(x==2)
        {
            t->status="Denied";
        }
        data.s[i]=t;
        }
    }
    if(!f)
    {
        cout<<"No passes applied"<<endl;
    }
}

void checkStatus(int n) {
    HANDLE h = GetStdHandle(STD_OUTPUT_HANDLE);
    int id,i,f=0;
    cout << "Enter application ID: ";
    cin >> id;
    for(i=0;i<n;i++) {
            if (data.id[i] == id) {
                    f=1;
                cout << "Name: " << data.s[i]->name << endl;
                cout << "Date of leaving: " << data.s[i]->dateLeave.day << "/" << data.s[i]->dateLeave.month << "/" << data.s[i]->dateLeave.year << endl;
                cout << "Date of arrival: " << data.s[i]->dateArrive.day << "/" << data.s[i]->dateArrive.month << "/" << data.s[i]->dateArrive.year << endl;
                cout << "Status: ";
                if (data.s[i]->status == "Pending") {
                    SetConsoleTextAttribute(h, 14);
                } else if (data.s[i]->status == "Approved") {
                    SetConsoleTextAttribute(h, 2);
                } else {
                    SetConsoleTextAttribute(h, 4);
                }
                cout << data.s[i]->status << endl;
               SetConsoleTextAttribute(h, 7);
            }
        }
        if(!f)
        {
            cout<<"Invalid application number"<<endl;
        }
}
int main() {
    int choice,n=0;
    Student temp;
    string password;

    root = insertNode(root, 6109, "Nitin", 49, "Krithick", 69);
    root = insertNode(root, 6111, "Kavi", 20, "Jai", 18);
    root = insertNode(root, 6112, "Hari", 31, "Naresh", 42);
    root = insertNode(root, 6113, "Karthick", 4, "Liwin", 36);
    root = insertNode(root, 6114, "Allan", 24, "Agathiyan", 28);


    while (true) {
        cout << "Enter \n1. Student portal\n2. RC portal\n3. Exit: ";
        cin >> choice;
        if (choice == 1) {
            cout << "Enter\n 1. Apply pass\n2. Check status: ";
            cin >> choice;
            if (choice == 1) {
                temp.getData();
                n++;
            } else if (choice == 2) {
                checkStatus(n);
            }
        } else if (choice == 2) {
            cout << "Enter your password: ";
            cin>>password;
            if (password == "Kavi@2006") {
                printData(n);
            } else {
                cout << "Wrong password" << endl;
            }
        } else if (choice == 3) {
            break;
        }
        cout<<endl;
        cout<<"-----------------------------------------------------------------------------------------------------";
        cout<<endl;
    }
    return 0;
}
