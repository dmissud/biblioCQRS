import {Component, inject} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {MatIconModule} from '@angular/material/icon';
import {CommandService} from '../../services/command.service';

@Component({
  selector: 'app-referencement',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule,
    MatFormFieldModule, MatInputModule, MatButtonModule, MatCardModule, MatSnackBarModule, MatIconModule
  ],
  templateUrl: './referencement.component.html',
  styleUrls: ['./referencement.component.css']
})
export class ReferencementComponent {
  private fb = inject(FormBuilder);
  private commandService = inject(CommandService);
  private snackBar = inject(MatSnackBar);

  ouvrageForm: FormGroup = this.fb.group({
    isbn: ['', Validators.required],
    titre: ['', Validators.required],
    auteur: ['', Validators.required]
  });

  exemplaireForm: FormGroup = this.fb.group({
    isbnOuvrage: ['', Validators.required],
    codeBarre: ['', Validators.required],
    salle: ['', Validators.required],
    etagere: ['', Validators.required],
    position: ['', Validators.required]
  });

  genererCodeBarre() {
    const code = 'CB-' + Math.random().toString(36).substring(2, 10).toUpperCase();
    this.exemplaireForm.patchValue({codeBarre: code});
  }

  referencerOuvrage() {
    if (this.ouvrageForm.valid) {
      this.commandService.referencerOuvrage(this.ouvrageForm.value).subscribe({
        next: () => {
          this.snackBar.open('Ouvrage référencé avec succès !', 'OK', { duration: 3000 });
          this.ouvrageForm.reset();
        },
        error: (err) => {
          this.snackBar.open('Erreur: ' + (err.error?.message || err.message), 'Fermer', { duration: 5000 });
        }
      });
    }
  }

  ajouterExemplaire() {
    if (this.exemplaireForm.valid) {
      const formValue = this.exemplaireForm.value;
      const isbn = formValue.isbnOuvrage;
      this.commandService.ajouterExemplaire(isbn, {
        codeBarre: formValue.codeBarre,
        salle: formValue.salle,
        etagere: formValue.etagere,
        position: formValue.position
      }).subscribe({
        next: () => {
          this.snackBar.open('Exemplaire ajouté avec succès !', 'OK', { duration: 3000 });
          this.exemplaireForm.reset();
        },
        error: (err) => {
          this.snackBar.open('Erreur: ' + (err.error?.message || err.message), 'Fermer', { duration: 5000 });
        }
      });
    }
  }
}
