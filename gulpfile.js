const gulp = require('gulp');
const concat = require('gulp-concat');
const rename = require("gulp-rename");

gulp.task('prepare-js', function () {
    return gulp.src(['build/webapp/*.js'])
        .pipe(concat('angular.js'))
        .pipe(gulp.dest('src/main/webapp/dist'));
});

gulp.task('prepare-css', function () {
    return gulp.src(['build/webapp/styles.*.css'])
        .pipe(rename('styles.css'))
        .pipe(gulp.dest('src/main/webapp/dist'));
});

gulp.task('build', gulp.parallel('prepare-js', 'prepare-css'));