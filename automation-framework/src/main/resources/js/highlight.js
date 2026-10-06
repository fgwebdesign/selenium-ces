if (!document.getElementById('tatf-highlight-style')) {
    var style = document.createElement('style');
    style.id = 'tatf-highlight-style';
    style.innerHTML = `
        .tatf-highlight {
            border-color: rgb(163, 230, 53) !important;
            animation: tatf-ring-pulse 1.6s ease-out infinite;
        }
        @keyframes tatf-ring-pulse {
            0%   { box-shadow: 0 0 0 0 rgba(163, 230, 53, 0.55); }
            70%  { box-shadow: 0 0 0 10px rgba(163, 230, 53, 0); }
            100% { box-shadow: 0 0 0 0 rgba(163, 230, 53, 0); }
        }
        .tatf-badge {
            position: absolute;
            display: flex;
            align-items: center;
            gap: 5px;
            background: rgb(163, 230, 53);
            color: #1a1a1a;
            font-family: Arial, sans-serif;
            font-size: 10.5px;
            font-weight: 600;
            padding: 3px 9px 3px 7px;
            border-radius: 999px;
            box-shadow: 0 4px 12px rgba(163, 230, 53, 0.5);
            z-index: 2147483647;
            pointer-events: none;
        }
        .tatf-dot {
            width: 5px;
            height: 5px;
            border-radius: 50%;
            background: #1a1a1a;
            animation: tatf-dot-blink 1s ease-in-out infinite;
        }
        @keyframes tatf-dot-blink {
            0%, 100% { opacity: 1; }
            50%      { opacity: 0.25; }
        }
    `;
    document.head.appendChild(style);
}

var el = arguments[0];
el.classList.add('tatf-highlight');

var previous = document.getElementById('tatf-badge-current');
if (previous) previous.remove();

var rect = el.getBoundingClientRect();
var badge = document.createElement('div');
badge.id = 'tatf-badge-current';
badge.className = 'tatf-badge';
badge.innerHTML = '<span class="tatf-dot"></span>interactuando';
badge.style.top = (window.scrollY + rect.top - 22) + 'px';
badge.style.left = (window.scrollX + rect.right - 90) + 'px';

document.body.appendChild(badge);