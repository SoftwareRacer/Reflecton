//
//  WiFiViewController.swift
//  Reflecton
//
//  Created by Alexander Jeitler-Stehr on 03.10.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

import UIKit

class WiFiViewController: UIViewController, UITextFieldDelegate {

    // define textfields as members
    @IBOutlet weak var textfieldSSID: UITextField!
    @IBOutlet weak var textfieldPSK: UITextField!
    
    // email passed by HomeViewController (Source SignInViewController)
    var email = ""
    
    // called after view is loaded into memory
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // set delegate
        textfieldSSID.delegate = self
        textfieldPSK.delegate = self
        
        // add observers
        NotificationCenter.default.addObserver(self, selector: #selector(SignUpViewController.keyboardWillShow(sender:)), name: NSNotification.Name.UIKeyboardWillShow, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(SignUpViewController.keyboardWillHide(sender:)), name: NSNotification.Name.UIKeyboardWillHide, object: nil)
    }
    
    // sent to view controller when the app receives a memory warning
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // called when button is clicked
    @IBAction func buttonConnect(_ sender: UIButton) {
        // address of the php-interface
        let urlInterface = URL(string: "http://\(ipAddressMirror)/ReflectonSetup/setup.php")
        
        // values from text fields
        let ssid = textfieldSSID.text
        let psk = textfieldPSK.text
        
        // check if the textfields are not empty
        if !ssid!.isEmpty && !psk!.isEmpty {
            // creating the post parameter by concatenating the keys and values from text field
            let postParameters = "email=" + self.email + "&ssid=" + ssid! + "&psk=" + psk!
            
            // creating request
            var request = URLRequest(url: urlInterface!)
            request.httpMethod = "POST"
            request.httpBody = postParameters.data(using: .utf8)
            
            // creating a task to send the post request
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
                
                // string conversion
                _ = String(data: data, encoding: .utf8)
            }
            // execute task
            task.resume()
        }
            
        // alert user when at least one textfield is empty
        else {
            // instantiate alert controller
            let alertController = UIAlertController(title: "Reflecton WiFi Controller", message: "Please check your entries!", preferredStyle: UIAlertControllerStyle.alert)
            
            // add action
            alertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default,handler: nil))
            
            // present the alert controller
            self.present(alertController, animated: true, completion: nil)
        }
    }
    
    // called when button is pressed
    @IBAction func backButton(_ sender: UIButton) {
        // custom segue (identifier set in Main.storyboard)
        self.performSegue(withIdentifier: "SegueToLeft", sender: sender)
    }
    
    // function is needed to prepare for the custom segue (mainly for transmitting email)
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // instantiate view controller
        let nextViewController = segue.destination as! HomeViewController
        
        // pass email
        nextViewController.email = self.email
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
        textfieldSSID.resignFirstResponder()
        textfieldPSK.resignFirstResponder()
        
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
