import { NgModule } from '@angular/core';
import { ServiceWorkerModule } from '@angular/service-worker';
import { APP_CONFIG, O_AUTH_SERVICE, ONTIMIZE_MODULES, ONTIMIZE_PROVIDERS, OntimizeWebModule } from 'ontimize-web-ngx';
import { OKeycloakMultitenantAuthService, OntimizeKeycloakMultitenantModule, O_MULTITENANT_CONFIG } from 'ontimize-web-ngx-keycloak';

import { environment } from '../environments/environment';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CONFIG, MULTITENANT_CONFIG } from './app.config';
import { MainService } from './shared/services/main.service';

// Defining custom providers (if needed)...
export const customProviders: any = [
  MainService,
  { provide: O_AUTH_SERVICE, useValue: OKeycloakMultitenantAuthService },
  { provide: O_MULTITENANT_CONFIG, useValue: MULTITENANT_CONFIG }
];

@NgModule({
  imports: [
    ONTIMIZE_MODULES,
    OntimizeWebModule,
    AppRoutingModule,
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production }),
    OntimizeKeycloakMultitenantModule
  ],
  declarations: [
    AppComponent
  ],
  bootstrap: [
    AppComponent
  ],
  providers: [
    { provide: APP_CONFIG, useValue: CONFIG },
    ONTIMIZE_PROVIDERS,
    ...customProviders
  ],
})
export class AppModule { }
