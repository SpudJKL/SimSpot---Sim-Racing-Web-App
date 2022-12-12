import { Component, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { Routes, RouterModule } from '@angular/router';
import { CategoryMenuComponent } from './components/category-menu/category-menu.component';
import { SearchComponent } from './components/search/search.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { CartStatusComponent } from './components/cart-status/cart-status.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CartDetailsComponent } from './components/cart-details/cart-details.component';
import { LandingpageComponent } from './components/landingpage/landingpage.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardUserComponent } from './components/board-user/board-user.component';
import { BoardAdminComponent } from './components/board-admin/board-admin.component';
import { AddProductComponent } from './components/add-product/add-product.component';
import { HomeComponent } from './home/home.component';
import { OrderHistoryComponent } from './components/order-history/order-history.component';
import { ProductCrudDetailsComponent } from './components/product-crud-details/product-crud-details.component';
import { ProductCrudListComponent } from './components/product-crud-list/product-crud-list.component';



const routes: Routes = [
  { path: 'Products/:id', component: ProductCrudDetailsComponent },
  { path: 'Products', component: ProductCrudListComponent },
  { path: 'admin', component: ProductCrudListComponent },
  { path: 'create', component: AddProductComponent },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'cart-details', component: CartDetailsComponent },
  { path: 'products/:id', component: ProductDetailsComponent },
  { path: 'search/:keyword', component: ProductListComponent },
  { path: 'category/:id', component: ProductListComponent },
  { path: 'category', component: ProductListComponent },
  { path: 'products', component: ProductListComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', redirectTo: '/home', pathMatch: 'full' },
];

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardUserComponent,
    AppComponent,
    ProductListComponent,
    CategoryMenuComponent,
    SearchComponent,
    ProductDetailsComponent,
    CartStatusComponent,
    CartDetailsComponent,
    LandingpageComponent,
    CheckoutComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardUserComponent,
    AddProductComponent,
    HomeComponent,
    OrderHistoryComponent,
    ProductCrudDetailsComponent,
    ProductCrudListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    RouterModule,
    NgbModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
