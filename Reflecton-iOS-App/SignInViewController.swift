//
//  SignInViewController.swift
//  Reflecton
//
//  Created by Alexander Jeitler-Stehr on 13.09.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

import UIKit

class SignInViewController: UIViewController, UITextFieldDelegate {

    // define textfields as members
    @IBOutlet weak var textfieldEmail: UITextField!
    @IBOutlet weak var textfieldPassword: UITextField!
    
    // called after view is loaded into memory
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // set delegate
        textfieldEmail.delegate = self
        textfieldPassword.delegate = self
        
        // add observers
        NotificationCenter.default.addObserver(self, selector: #selector(SignInViewController.keyboardWillShow(sender:)), name: NSNotification.Name.UIKeyboardWillShow, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(SignInViewController.keyboardWillHide(sender:)), name: NSNotification.Name.UIKeyboardWillHide, object: nil)
    }
    
    // sent to view controller when the app receives a memory warning
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // called when button is pressed
    @IBAction func buttonSignIn(_ sender: UIButton) {
        // address of the php-interface
        let urlInterface = URL(string: "http://\(ipAddressServer)/Reflecton/api/signin.php")
        
        // values from text fields
        let email = textfieldEmail.text
        let password = textfieldPassword.text
        
        // check if the textfields are not empty
        if !email!.isEmpty && !password!.isEmpty {
            // creating the post parameter by concatenating the values of each textfield
            let postParameters = "email=" + email! + "&password=" + password!
            
            // creating request
            var request = URLRequest(url: urlInterface!)
            request.httpMethod = "POST"
            request.httpBody = postParameters.data(using: .utf8)
            
            // asynchronous task to send the post request
            let task = URLSession.shared.dataTask(with: request) { data, response, error in
                // check if the transmission succeded
                guard let data = data, error == nil else {
                    print("error=\(String(describing: error))")
                    return
                }
                
                // check if status code is different than 200
                if let httpStatus = response as? HTTPURLResponse, httpStatus.statusCode != 200 {
                    print("statusCode should be 200, but is \(httpStatus.statusCode)")
                    print("response = \(String(describing: response))")
                }
                
                // put result into constant
                let responseString = String(data: data, encoding: .utf8)
                
                // call function
                self.switchViewController(sender, responseString: responseString!)
            }
            // execute task
            task.resume()
        }
        
        // alert user when at least one textfield is empty
        else {
            // instantiate alert controller
            let alertController = UIAlertController(title: "Reflecton Sign In", message: "Please check your entries!", preferredStyle: UIAlertControllerStyle.alert)
            
            // add action
            alertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default,handler: nil))
            
            // present the alert controller
            self.present(alertController, animated: true, completion: nil)
        }
    }
    
    // declaration of switch view controller function
    func switchViewController(_ sender: UIButton, responseString :String?) {
        // convert string to int
        let responseInt = Int(responseString!)
        
        // print response
        print("response = \(responseInt!)")
        
        // asynchronous task to switch the view controller
        DispatchQueue.main.async(execute: {
            // switch view controller when user input was correct
            if responseInt! == 1 {
                // custom segue (identifier set in Main.storyboard)
                self.performSegue(withIdentifier: "SegueToBottom", sender: sender)
            }
            
            // create alert (no view controller switching) when user input was incorrect
            else {
                // instantiate alert controller
                let alertController = UIAlertController(title: "Reflecton Sign In", message: "E-Mail and password do not match to each other!", preferredStyle: UIAlertControllerStyle.alert)
                
                // add action
                alertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default,handler: nil))
                
                // present the alert controller
                self.present(alertController, animated: true, completion: nil)
            }
        })
    }
    
    // called when button is pressed
    @IBAction func buttonJoin(_ sender: UIButton) {
        // custom segue (identifier set in Main.storyboard)
        self.performSegue(withIdentifier: "SegueToRight", sender: sender)
    }
    
    // function is needed to prepare for the custom segue (mainly for transmitting email)
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // check if identifier is SegueToBottom (called through buttonSignIn)
        if segue.identifier == "SegueToBottom" {
            // instantiate view controller
            let nextViewController = segue.destination as! HomeViewController
        
            // pass email
            nextViewController.email = self.textfieldEmail.text!
        }
        // check if identifier is SegueToRight (called through buttonJoin)
        else if segue.identifier == "SegueToRight" {
            // instantiate view controller
            _ = segue.destination as! SignUpViewController
        }
    }
    
    // hide keyboard when return key is pressed
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        // end editing
        textField.resignFirstResponder()
        
        // return acknowledge
        return true
    }
    
    // hide keyboard when user clicks somewhere else
    override func touchesBegan(_: Set<UITouch>, with: UIEvent?) {
        // resign first responder
        self.textfieldEmail.resignFirstResponder()
        self.textfieldPassword.resignFirstResponder()
        
        // end editing
        self.view.endEditing(true)
    }
    
    // move up the view when the keyboard is displayed
    @objc func keyboardWillShow(sender: NSNotification) {
        // move view 150 points upward
        self.view.frame.origin.y = -150
    }
    
    // move down the screen to the original position when the keyboard is hidden
    @objc func keyboardWillHide(sender: NSNotification) {
        // move view to original position
        self.view.frame.origin.y = 0
    }
    
}

