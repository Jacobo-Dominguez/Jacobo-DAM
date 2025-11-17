document.addEventListener('DOMContentLoaded', function () {
  const btn = document.getElementById('theme-toggle');
  // Detectar tema guardado o preferencia del sistema
  const saved = localStorage.getItem('theme');
  const prefersLight = window.matchMedia && window.matchMedia('(prefers-color-scheme: light)').matches;
  const initial = saved || (prefersLight ? 'light' : 'dark');

  function applyTheme(t) {
    if (t === 'light') {
      document.body.classList.add('light');
      if (btn) btn.textContent = '🌞';
    } else {
      document.body.classList.remove('light');
      if (btn) btn.textContent = '🌙';
    }
    try { localStorage.setItem('theme', t); } catch (e) { /* ignore */ }
  }

  // Inicializar
  applyTheme(initial);

  if (btn) {
    btn.addEventListener('click', function () {
      const isLight = document.body.classList.contains('light');
      applyTheme(isLight ? 'dark' : 'light');
    });
  }

  // Escuchar cambios en preferencia del sistema (opcional)
  if (window.matchMedia) {
    const mq = window.matchMedia('(prefers-color-scheme: light)');
    mq.addEventListener && mq.addEventListener('change', function (e) {
      const saved = localStorage.getItem('theme');
      if (!saved) { // solo cambiar si el usuario no ha elegido explícitamente
        applyTheme(e.matches ? 'light' : 'dark');
      }
    });
  }
});
