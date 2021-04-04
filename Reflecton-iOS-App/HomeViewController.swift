//
//  HomeViewController.swift
//  Reflecton
//
//  Created by Alexander Jeitler-Stehr on 19.09.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

import UIKit

class HomeViewController: UIViewController, UICollectionViewDataSource {
    
    // define collection view as member
    @IBOutlet weak var collectionView: UICollectionView!
    
    // define widgets-string as member
    fileprivate var widgets: [String] = []
    
    // define long press gesture
    fileprivate var longPressGesture: UILongPressGestureRecognizer!
    
    // email passed by SignInViewController
    var email = ""
    
    // called after view is loaded into memory
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // set background color to clear color (otherwise it would be black)
        self.collectionView.backgroundColor = UIColor.clear
        
        // add long press gesture to collection view
        longPressGesture = UILongPressGestureRecognizer(target: self, action: #selector(HomeViewController.handleLongGesture(_:)))
        self.collectionView.addGestureRecognizer(longPressGesture)
        
        // load userdata on startup
        self.loadUserData()
    }
    
    // sent to view controller when the app receives a memory warning
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // called when button is pressed
    @IBAction func settingsButton(_ sender: UIButton) {
        // custom segue (identifier set in Main.storyboard)
        self.performSegue(withIdentifier: "SegueToRight", sender: sender)
    }
    
    // function is needed to prepare for the custom segue (mainly for transmitting email)
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // instantiate view controller
        let nextViewController = segue.destination as! WiFiViewController
        
        // pass email
        nextViewController.email = self.email
    }
    
    // return number of widgets
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        // return number of widgets
        return widgets.count
    }
    
    // specify cells (preset)
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        // define cell of collection view
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "Cell", for: indexPath) as! TextCollectionViewCell
        
        // set to default
        var image: UIImage = UIImage()
        cell.textLabel.text = ""
        
        // check if string (widgets[indexPath.item]) contains email
        if widgets[indexPath.item].range(of: "email") != nil {
            // set image
            image = UIImage(named: "WidgetEmail")!
        }
        // check if string (widgets[indexPath.item]) contains calendar
        else if widgets[indexPath.item].range(of: "calendar") != nil {
            // set image
            image = UIImage(named: "WidgetCalendar")!
        }
        // check if string (widgets[indexPath.item]) contains clock
        else if widgets[indexPath.item].range(of: "clock") != nil {
            // set image
            image = UIImage(named: "WidgetClock")!
        }
        // check if string (widgets[indexPath.item]) contains weather
        else if widgets[indexPath.item].range(of: "weather") != nil {
            // set image
            image = UIImage(named: "WidgetWeather")!
        }
        // no string is detected
        else {
            // set text
            cell.textLabel.text = widgets[indexPath.item]
        }
        
        // add image to cell
        let imageView = UIImageView(image: image)
        cell.backgroundView = UIView()
        cell.backgroundView!.addSubview(imageView)
        cell.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(tap(_:))))
        
        // return
        return cell
    }
    
    // widget is moved
    func collectionView(_ collectionView: UICollectionView, moveItemAt sourceIndexPath: IndexPath, to destinationIndexPath: IndexPath) {
        // remove widget at source
        let temp = widgets.remove(at: sourceIndexPath.item)
        
        // add widget at destination
        widgets.insert(temp, at: destinationIndexPath.item)
        
        // instantiate data string
        var data = ""
        
        // build data string
        for i in 0..<widgets.count {
            data = data + "<widget>" + widgets[i] + "</widget>"
        }
        
        // set user data
        _ = self.setUserData(data: data)
    }
    
    // tap gesture which opens an user input
    @objc func tap(_ sender: UITapGestureRecognizer) {
        
        // location of sender
        let location = sender.location(in: self.collectionView)
        
        // index path
        let indexPath = self.collectionView.indexPathForItem(at: location)
        
        // check if string (widgets[indexPath.item]) contains email
        if widgets[(indexPath?.item)!].range(of: "email") != nil {
            
            // instantiate alert controller
            let alertController = UIAlertController(title: "Add E-Mail Account", message: "Enter your E-Mail and Password", preferredStyle: UIAlertControllerStyle.alert)
            
            // add text field
            alertController.addTextField(configurationHandler: { (textField) in
                // add placeholder text
                textField.placeholder = "E-Mail"
                
                // define keyboard type
                textField.keyboardType = .emailAddress
            })
            
            // add text field
            alertController.addTextField(configurationHandler: { (textField) in
                // add placeholder text
                textField.placeholder = "Password"
                
                // define keyboard type
                textField.isSecureTextEntry = true
            })
            
            // add button (called when clicked on OK)
            alertController.addAction(UIAlertAction(title: "OK", style: .default, handler: {
                (action) in
                
                // get text of email
                let textEmail = alertController.textFields![0] as UITextField
                
                // get text of password
                let textPassword = alertController.textFields![1] as UITextField
                
                // instatiate data string
                var data = ""
                
                // build data string
                for i in 0..<self.widgets.count {
                    // check if string (widgets[indexPath.item]) contains interactive field email
                    if self.widgets[i].range(of: "email") != nil {
                        // add component with further parameters (parameter is expressed through ":")
                        data = data + "<widget>" + self.widgets[i] + ":" + textEmail.text! + ":" + textPassword.text! + "</widget>"
                    }
                    // check if string (widgets[indexPath.item]) contains non-interactive field
                    else {
                        // add component with no further parameters
                        data = data + "<widget>" + self.widgets[i] + "</widget>"
                    }
                }
                
                // instantiate dispatch group
                let myGroup = DispatchGroup()
                
                // enter group
                myGroup.enter()
                
                // check if the transmission of setuserdata was successful
                if self.setUserData(data: data) == 1 {
                    // leave group
                    myGroup.leave()
                }
                
                // called when group is left
                myGroup.notify(queue: DispatchQueue.main) {
                    // load userdata
                    self.loadUserData()
                }
            }))
            
            // add button
            alertController.addAction(UIAlertAction(title: "Cancel", style: UIAlertActionStyle.default,handler: nil))
            
            // present alert view controller
            self.present(alertController, animated: true, completion: nil)
        }
    }
    
    // long hold gesture
    @objc func handleLongGesture(_ gesture: UILongPressGestureRecognizer) {
        // check gesture state
        switch(gesture.state) {
        // recognized as a continuous gesture
        case UIGestureRecognizerState.began:
            // get index path
            guard let selectedIndexPath = self.collectionView.indexPathForItem(at: gesture.location(in: self.collectionView))
            else {
                break
            }
            // begin movement
            collectionView.beginInteractiveMovementForItem(at: selectedIndexPath)
            
        // recognized as a change to a continuous gesture
        case UIGestureRecognizerState.changed:
            // update movement
            collectionView.updateInteractiveMovementTargetPosition(gesture.location(in: gesture.view!))
        
        // recognized as the end of a continuous gesture
        case UIGestureRecognizerState.ended:
            // end movement
            collectionView.endInteractiveMovement()
            
        // default
        default:
            // cancel movemont
            collectionView.cancelInteractiveMovement()
        }
    }
    
    // get user-specific data from the database
    func loadUserData() {
        // get userdata in order to arrange widgets
        self.getUserData { jsonString in
            // convert jsonString to string
            let widgetsArrangement = String(jsonString)
            print("LoadUserData: " + widgetsArrangement)
            
            // format: widget-tags -> slashes -> array-elements
            // format by replacing widget-tags with slashes
            var widgetsArrangementString = widgetsArrangement.replacingOccurrences(of: "</widget>", with: "/").replacingOccurrences(of: "<widget>", with: "")
            
            // format by deleting last slash
            widgetsArrangementString.removeLast()
            
            // split into array by slash
            let widgetsArrangementArray = widgetsArrangementString.components(separatedBy: "/")
            
            // delete content
            self.widgets.removeAll()
            
            // arrange widgets by userdata from database
            for i in 0..<widgetsArrangementArray.count {
                self.widgets.append(widgetsArrangementArray[i])
            }
            
            // reload data
            DispatchQueue.main.async {
                self.collectionView.reloadData()
            }
        }
    }
    
    // get user-specific data from the database
    func getUserData(completion:  @escaping (String) -> ()) {
        // address of the php-interface
        let urlInterface = URL(string: "http://\(ipAddressServer)/Reflecton/api/getdata.php")
        
        // check if the email member is not empty
        if !self.email.isEmpty {
            // creating the post parameter
            let postParameters = "email=" + self.email
            
            // creating request
            var request = URLRequest(url: urlInterface!)
            request.httpMethod = "POST"
            request.httpBody = postParameters.data(using: .utf8)
            
            // creating a task to send the post request
            let task = URLSession.shared.dataTask(with: request) {
                data, response, error in
                // check if the transmission succeded
                if let data = data, let jsonString = String(data: data, encoding: String.Encoding.utf8), error == nil {
                    completion(jsonString)
                }
                // print error otherwise
                else {
                    print("error=\(error!.localizedDescription)")
                }
            }
            // execute task
            task.resume()
        }
    }
    
    // set user-specific data to the database
    func setUserData(data: String) -> Int {
        // address of the php-interface
        let urlInterface = URL(string: "http://\(ipAddressServer)/Reflecton/api/setdata.php")
        
        // check if email and data is not empty
        if !self.email.isEmpty && !data.isEmpty {
            // creating the post parameter by concatenating the keys and values from text field
            let postParameters = "email=" + self.email + "&data=" + data
            print("SetUserData: " + data)
            
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
            
            // return flag
            return 1
        }
        // called when email or data is empty
        else {
            // return flag
            return 0
        }
    }
}
