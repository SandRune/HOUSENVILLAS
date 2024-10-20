//Admin Login
BEGIN
  INPUT username, password
  SEARCH username in Whitelisted Database

  IF username found THEN
    VALIDATE password
    IF password is correct THEN
      SEARCH role of the user in Database
      
      IF role is 'admin' THEN
        REDIRECT to Admin Page
      ELSE
        REDIRECT to User Page
      ENDIF
    ELSE
      DISPLAY "Incorrect password"
    ENDIF
  ELSE
    DISPLAY "User not found"
    PROMPT to register a new account
    INPUT registration details (username, password, etc.)
    STORE new account in Database
  ENDIF
END

//Admin Page
BEGIN
  DISPLAY "Welcome to Admin Page"
  DISPLAY "1. Admin Host Page"
  DISPLAY "2. Admin User Page"
  DISPLAY "3. Customer Service"
  DISPLAY "4. Message (user/host/other admin)"
  DISPLAY "5. Whitelist a new admin"
  
  INPUT admin_choice

  SWITCH admin_choice
    CASE 1:
      REDIRECT to Admin Host Page
    CASE 2:
      REDIRECT to Admin User Page
    CASE 3:
      REDIRECT to Customer Service
    CASE 4:
      INPUT recipient (user, host, or other admin)
      INPUT message content
      SEND message to recipient
    CASE 5:
      INPUT new_admin_details (username, email, password)
      STORE new admin to Whitelisted Database
      DISPLAY "New admin whitelisted successfully"
    DEFAULT:
      DISPLAY "Invalid choice, please try again"
  ENDSWITCH
END

//Admin Host Page - Property Approval
BEGIN
  DISPLAY "Welcome to Admin Host Page"
  DISPLAY "List of Host Property Requests"
  
  FOR each property_request in PendingRequests
    DISPLAY property details (host_name, property_address, property_description, etc.)
    
    DISPLAY "Do you approve this property? (y/n)"
    INPUT approval_decision

    IF approval_decision = 'y' THEN
      CHECK property standards against Company Guidelines
      IF property meets standards THEN
        APPROVE property
        NOTIFY host of approval
        ADD property to Active Listings
        DISPLAY "Property approved and listed successfully"
      ELSE
        DISPLAY "Property does not meet standards"
        NOTIFY host to make improvements
      ENDIF
    ELSE
      REJECT property
      DISPLAY "Property rejected"
      NOTIFY host of rejection
    ENDIF
  ENDFOR
END

    //Host Property Removal
    BEGIN
      DISPLAY "Welcome to Host Property Management"
      DISPLAY "List of Active Properties"
      
      INPUT property_selection

      DISPLAY "Do you want to remove this property from the rental listing? (y/n)"
      INPUT remove_decision
      
      IF remove_decision = 'y' THEN
        CHECK removal request against Company Guidelines
        IF removal meets guidelines THEN
          REMOVE property from Active Listings
          UPDATE property status to "Inactive"
          NOTIFY admin and host of removal
          DISPLAY "Property removed successfully"
        ELSE
          DISPLAY "Cannot remove property: does not meet removal guidelines"
        ENDIF
      ELSE
        DISPLAY "Property removal canceled"
      ENDIF
    END

//Admin User Page - Payment
BEGIN
  DISPLAY "Welcome to Admin User Page"
  DISPLAY "List of Active Rentals"

  FOR each rental in ActiveRentals
    DISPLAY rental details (user_name, host_name, property, rental_duration, rental_status)
    
    IF rental_status = "Completed" THEN
      CHECK if rental duration is over
      IF rental duration is over THEN
        TRANSFER held money to Host
        UPDATE rental status to "Completed - Payment Released"
        NOTIFY host of payment release
        DISPLAY "Payment released to host"
      ENDIF
    ENDIF
  ENDFOR

  DISPLAY "Refund Requests"
  FOR each refund_request in RefundRequests
    DISPLAY refund details (user_name, rental_id, reason for refund)

    CHECK refund request against Company Guidelines
    IF refund request meets guidelines THEN
      APPROVE refund
      RETURN held money to User
      NOTIFY user of refund approval
      DISPLAY "Refund approved and returned to user"
    ELSE
      REJECT refund
      NOTIFY user of rejection
      DISPLAY "Refund request rejected"
    ENDIF
  ENDFOR
END

//Customer Service
BEGIN
  DISPLAY "Welcome to Customer Service Page"
  DISPLAY "List of Customer Service Requests"

  FOR each request in CustomerServiceRequests
    DISPLAY request details (request_id, user/host_name, issue_description, date)
    
    DISPLAY "1. View details"
    DISPLAY "2. Message the messager directly"
    INPUT admin_choice

    IF admin_choice = 1 THEN
      DISPLAY full request details (conversation history, issue, etc.)
    ELSEIF admin_choice = 2 THEN
      PROMPT "Do you want to message the messager? (y/n)"
      INPUT recipient_choice
      IF recipient_choice = 'y' THEN
        REDIRECT to Message Page for massager
      ELSEIF recipient_choice = 'n' THEN
        REDIRECT to Message Page 
      ENDIF
    ENDIF
  ENDFOR
END

//Message
BEGIN
  DISPLAY "Welcome to Customer Service Page"
  DISPLAY "List of Customer Service Requests"

  FOR each request in CustomerServiceRequests
    DISPLAY request details (request_id, user/host_name, issue_description, date)
    
    DISPLAY "1. View details"
    DISPLAY "2. Message the user/host"
    INPUT admin_choice

    IF admin_choice = 1 THEN
      DISPLAY full request details (conversation history, issue, etc.)
    ELSEIF admin_choice = 2 THEN
      REDIRECT to Message Page
    ENDIF
  ENDFOR
END

//Whitelisting a New Admin
BEGIN
  DISPLAY "Welcome to Whitelisting New Admin"
  
  INPUT new_admin_details (username, email, password)

  CHECK if username already exists in Whitelisted Database
  IF username exists THEN
    DISPLAY "Username already exists. Please choose a different username."
  ELSE
    VALIDATE email format
    IF email is valid THEN
      VALIDATE password strength
      IF password is strong THEN
        STORE new admin in Whitelisted Database
        DISPLAY "New admin whitelisted successfully"
      ELSE
        DISPLAY "Password does not meet strength requirements"
      ENDIF
    ELSE
      DISPLAY "Invalid email format"
    ENDIF
  ENDIF
END