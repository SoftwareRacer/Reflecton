//
//  DesignableTextField.swift
//  Reflecton
//
//  Created by Alexander Jeitler-Stehr on 27.09.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

import UIKit

@IBDesignable class DesignableTextField: UITextField {
    
    // left image
    @IBInspectable var leftImage: UIImage? {
        // changement
        didSet {
            // update view
            self.updateView()
        }
    }
    
    // left padding
    @IBInspectable var leftPadding: CGFloat = 5 {
        // changement
        didSet {
            // update view
            self.updateView()
        }
    }
    
    // corner radius
    @IBInspectable var cornerRadius: CGFloat = 0 {
        // changement
        didSet {
            // set corner radius
            self.layer.cornerRadius = cornerRadius
        }
    }
    
    // update view
    func updateView() {
        // condition true if initialization succeeded
        if let image = leftImage {
            // defines the times at which overlay views appear in a text field
            self.leftViewMode = .always
            
            // image
            let imageView = UIImageView(frame: CGRect(x: leftPadding, y: 0, width: 40, height: 40))
            imageView.image = image
            imageView.tintColor = tintColor
            
            // define with
            var width = leftPadding + 30
            
            // check border style
            if borderStyle == UITextBorderStyle.none || borderStyle == UITextBorderStyle.line {
                // add 20 to width
                width = width + 20;
            }
            
            // define view
            let view = UIView(frame: CGRect(x: 0, y: 0, width: width, height: 40))
            view.addSubview(imageView)
            
            // add view to left view
            self.leftView = view
        }
        // otherwise
        else {
            // defines the times at which overlay views appear in a text field
            self.leftViewMode = .never
        }
        
        // placeholder
        self.attributedPlaceholder = NSAttributedString(string: placeholder!, attributes: [NSAttributedStringKey.foregroundColor : tintColor!])
    }
}
