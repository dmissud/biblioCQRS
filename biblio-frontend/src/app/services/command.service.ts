import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ReferencerOuvrageRequest {
  isbn: string;
  titre: string;
  auteur: string;
}

export interface AjouterExemplaireRequest {
  codeBarre: string;
  salle: string;
  etagere: string;
  position: string;
}

@Injectable({
  providedIn: 'root'
})
export class CommandService {
  private http = inject(HttpClient);

  referencerOuvrage(request: ReferencerOuvrageRequest): Observable<void> {
    return this.http.post<void>('/api/ouvrages', request);
  }

  ajouterExemplaire(isbn: string, request: AjouterExemplaireRequest): Observable<void> {
    return this.http.post<void>(`/api/ouvrages/${isbn}/exemplaires`, request);
  }
}
