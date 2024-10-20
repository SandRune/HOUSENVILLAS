#include <stdio.h>
#include <string.h>

#define MAX_USERS 100
#define MAX_PROPERTIES 100
#define MAX_REQUESTS 100
#define MAX_ADMINS 10

typedef struct {
    char username[50];
    char password[50];
    char role[10]; // "user" or "admin"
} User;

typedef struct {
    char host_name[50];
    char property_address[100];
    char description[200];
    int is_active; // 1 for active, 0 for inactive
} Property;

typedef struct {
    char user_name[50];
    char issue_description[200];
} CustomerServiceRequest;

typedef struct {
    char username[50];
    char email[100];
    char password[50];
} Admin;

User users[MAX_USERS];
Property properties[MAX_PROPERTIES];
CustomerServiceRequest serviceRequests[MAX_REQUESTS];
Admin admins[MAX_ADMINS];
int userCount = 0, propertyCount = 0, requestCount = 0, adminCount = 0;

void registerUser() {
    // Registration logic
    printf("Registering new user...\n");
    // Implementation can be added
}

void login() {
    char username[50], password[50];
    printf("Enter username: ");
    scanf("%s", username);
    printf("Enter password: ");
    scanf("%s", password);
    
    // Check if user is in the database (simplified)
    for (int i = 0; i < userCount; i++) {
        if (strcmp(users[i].username, username) == 0 && strcmp(users[i].password, password) == 0) {
            if (strcmp(users[i].role, "admin") == 0) {
                printf("Admin logged in.\n");
                // Call admin functions
            } else {
                printf("User logged in.\n");
                // Call user functions
            }
            return;
        }
    }
    printf("Invalid username or password.\n");
}

void approveProperty() {
    // Property approval logic
    printf("Approving property...\n");
    // Implementation can be added
}

void handleCustomerService() {
    // Customer service handling logic
    printf("Handling customer service...\n");
    // Implementation can be added
}

void addAdmin() {
    if (adminCount < MAX_ADMINS) {
        printf("Enter new admin username: ");
        scanf("%s", admins[adminCount].username);
        printf("Enter new admin email: ");
        scanf("%s", admins[adminCount].email);
        printf("Enter new admin password: ");
        scanf("%s", admins[adminCount].password);
        adminCount++;
        printf("New admin whitelisted successfully.\n");
    } else {
        printf("Max admin limit reached.\n");
    }
}

int main() {
    int choice;
    
    while (1) {
        printf("1. Register User\n");
        printf("2. Login\n");
        printf("3. Add Admin\n");
        printf("4. Approve Property\n");
        printf("5. Customer Service\n");
        printf("6. Exit\n");
        printf("Choose an option: ");
        scanf("%d", &choice);
        
        switch (choice) {
            case 1:
                registerUser();
                break;
            case 2:
                login();
                break;
            case 3:
                addAdmin();
                break;
            case 4:
                approveProperty();
                break;
            case 5:
                handleCustomerService();
                break;
            case 6:
                return 0;
            default:
                printf("Invalid option. Try again.\n");
        }
    }
    
    return 0;
}
