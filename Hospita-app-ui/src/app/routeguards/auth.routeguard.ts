import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, Router } from '@angular/router';
import { User } from '../model/user.model';

@Injectable()
export class AuthActivateRouteGuard implements CanActivate {
    user: User | null = null; // Initialize user as null

    constructor(private router: Router) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        /*
        // Retrieve user details from session storage
        const userDetails = sessionStorage.getItem('userdetails');
        if (userDetails) {
            this.user = JSON.parse(userDetails);
        }
      
        // Check if the route requires authentication
        if (route.routeConfig && route.routeConfig.canActivate && route.routeConfig.canActivate.includes(AuthActivateRouteGuard)) {
            // Route requires authentication
            if (!this.user) {
                // User is not authenticated, redirect to login
                this.router.navigate(['login']);
                return false;
            }
        }
      */
        // Route does not require authentication or user is authenticated
        return true;
    }
}
