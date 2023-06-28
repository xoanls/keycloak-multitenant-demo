import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OKeycloakMultitenantAuthGuardService } from 'ontimize-web-ngx-keycloak';

export const routes: Routes = [
  {
    path: 'main',
    canActivate: [OKeycloakMultitenantAuthGuardService],
    loadChildren: () => import('./main/main.module').then(m => m.MainModule)
  },
  { path: 'login', loadChildren: () => import('./login/login.module').then(m => m.LoginModule) },
  { path: '', redirectTo: 'main', pathMatch: 'full' }
];

const opt = {
  enableTracing: false // true if you want to print navigation routes
};

@NgModule({
  imports: [RouterModule.forRoot(routes, opt)],
  exports: [RouterModule],
  providers: [OKeycloakMultitenantAuthGuardService]
})
export class AppRoutingModule { }
