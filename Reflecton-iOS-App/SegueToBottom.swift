//
//  SegueToBottom.swift
//  Reflecton
//
//  Created by Alexander Jeitler-Stehr on 31.10.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

import UIKit

class SegueToBottom: UIStoryboardSegue {
    override func perform() {
        let src = self.source
        let dst = self.destination
        
        src.view.superview?.insertSubview(dst.view, aboveSubview: src.view)
        dst.view.transform = CGAffineTransform(translationX: 0, y: src.view.frame.size.height)
        UIView.animate(withDuration: 0.25, delay: 0.0, options: UIViewAnimationOptions.curveEaseInOut, animations: {
            dst.view.transform = CGAffineTransform(translationX: 0, y: 0)
        }) { (finished) in
            src.present(dst, animated: false, completion: nil)
        }
    }
}
