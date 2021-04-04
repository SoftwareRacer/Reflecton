//
//  SignUpViewController.swift
//  Reflecton
//
//  Created by Alexander Jeitler-Stehr on 18.09.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

import UIKit

class SignUpViewController: UIViewController, UITextFieldDelegate {

    // define textfields as members
    @IBOutlet weak var textfieldFirstname: UITextField!
    @IBOutlet weak var textfieldLastname: UITextField!
    @IBOutlet weak var textfieldEmail: UITextField!
    @IBOutlet weak var textfieldPassword: UITextField!
    @IBOutlet weak var textfieldPasswordRepeat: UITextField!
    
    // called after view is loaded into memory
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // set delegate
        textfieldFirstname.delegate = self
        textfieldLastname.delegate = self
        textfieldEmail.delegate = self
        textfieldPassword.delegate = self
        textfieldPasswordRepeat.delegate = self
        
        // add observers
        NotificationCenter.default.addObserver(self, selector: #selector(SignUpViewController.keyboardWillShow(sender:)), name: NSNotification.Name.UIKeyboardWillShow, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(SignUpViewController.keyboardWillHide(sender:)), name: NSNotification.Name.UIKeyboardWillHide, object: nil)
    }
    
    // sent to view controller when the app receives a memory warning
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // called when button gets pressed
    @IBAction func buttonSignUp(_ sender: UIButton) {
        // address of the php-interface
        let urlInterface = URL(string: "http://\(ipAddressServer)/Reflecton/api/signup.php")
        
        // values from text fields
        let firstname = textfieldFirstname.text
        let lastname = textfieldLastname.text
        let email = textfieldEmail.text
        let password = textfieldPassword.text
        let passwordRepeat = textfieldPasswordRepeat.text
        
        // initial set of data (aka userdata in database)
        var data = ""
        data = data + "<widget>email</widget>"
        data = data + "<widget>calendar</widget>"
        data = data + "<widget>weather</widget>"
        data = data + "<widget>clock</widget>"
        
        // check if the textfields are not empty
        if !firstname!.isEmpty && !lastname!.isEmpty && !email!.isEmpty && !password!.isEmpty && password! == passwordRepeat! {
            // creating the post parameter by concatenating the values of each textfield
            let postParameters = "first_name=" + firstname! + "&last_name="+lastname! + "&email="+email! + "&password="+password! + "&data="+data
            
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
            let alertController = UIAlertController(title: "Reflecton Sign Up", message: "The passwords might not fit to each other, or a required field is missing!", preferredStyle: UIAlertControllerStyle.alert)
            
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
            // switch view controller when user input was correct (+ create alert)
            if responseInt! == 1 {
                // instantiate alert controller
                let alertController = UIAlertController(title: "Reflecton Sign Up", message: "You are now a member of pinnovations.", preferredStyle: UIAlertControllerStyle.alert)
                
                // add action
                let action = UIAlertAction(title: "OK", style: .default) { (action) -> Void in
                    // custom segue (identifier set in Main.storyboard)
                    self.performSegue(withIdentifier: "SegueToLeft", sender: sender)
                }
                alertController.addAction(action)
                
                // present the alert controller
                self.present(alertController, animated: true, completion: nil)
            }
                
            // create alert (no view controller switching) when user input was incorrect
            else {
                // instantiate alert controller
                let alertController = UIAlertController(title: "Reflecton Sign Up", message: "E-Mail already used!", preferredStyle: UIAlertControllerStyle.alert)
                
                // add action
                alertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default,handler: nil))
                
                // present the alert controller
                self.present(alertController, animated: true, completion: nil)
            }
        })
    }
    
    // called when button is pressed
    @IBAction func buttonBack(_ sender: UIButton) {
        // custom segue (identifier set in Main.storyboard)
        self.performSegue(withIdentifier: "SegueToLeft", sender: sender)
    }
    
    // function is needed to prepare for the custom segue
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // instantiate view controller
        _ = segue.destination as! SignInViewController
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
        textfieldFirstname.resignFirstResponder()
        textfieldLastname.resignFirstResponder()
        textfieldEmail.resignFirstResponder()
        textfieldPassword.resignFirstResponder()
        textfieldPasswordRepeat.resignFirstResponder()
        
        // end editing
        self.view.endEditing(true)
    }
    
    // move up the view when the keyboard is displayed
    @objc func keyboardWillShow(sender: NSNotification) {
        // move view 160 points upward
        self.view.frame.origin.y = -160
    }
    
    // move down the screen to the original position when the keyboard is hidden
    @objc func keyboardWillHide(sender: NSNotification) {
        // move view to original position
        self.view.frame.origin.y = 0
    }

}
